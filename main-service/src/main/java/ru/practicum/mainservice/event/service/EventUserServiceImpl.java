package ru.practicum.mainservice.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.repository.CategoryRepository;
import ru.practicum.mainservice.event.EventMapper;
import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.event.dto.EventShortDto;
import ru.practicum.mainservice.event.dto.NewEventDto;
import ru.practicum.mainservice.event.model.Event;
import ru.practicum.mainservice.event.repository.EventUserRepository;
import ru.practicum.mainservice.event.status.State;
import ru.practicum.mainservice.exception.NoAccessException;
import ru.practicum.mainservice.exception.NoSuchBodyException;
import ru.practicum.mainservice.exception.ReachedLimitException;
import ru.practicum.mainservice.exception.UnsupportedStatusException;
import ru.practicum.mainservice.request.RequestMapper;
import ru.practicum.mainservice.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;
import ru.practicum.mainservice.event.dto.UpdateEventUserRequest;
import ru.practicum.mainservice.request.model.ParticipationRequest;
import ru.practicum.mainservice.request.repository.RequestUserRepository;
import ru.practicum.mainservice.event.status.StateAction;
import ru.practicum.mainservice.request.status.RequestStatus;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventUserServiceImpl implements EventUserService {

    private final EventUserRepository eventUserRepository;
    private final CategoryRepository categoryRepository;
    private final RequestUserRepository requestUserRepository;
    private final UserRepository userRepository;
    private final EventMapper eventMapper;
    private final RequestMapper requestMapper;

    @Override
    public Collection<EventShortDto> getEvents(int userId, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Collection<Event> events = eventUserRepository.findAllByInitiatorId(userId, pageable).getContent();
        return events.stream().map(eventMapper::toEventShortDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ResponseEntity<EventFullDto> addEvent(int userId, NewEventDto newEventDto) {
        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                        "message", "Категория с id = " + newEventDto.getCategory() + " отсутствует",
                        "reason", "Указанная категория не найдена",
                        "status", HttpStatus.NOT_FOUND.name(),
                        "timeStamp", LocalDateTime.now())));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                        "message","Пользователь с id = " + userId + " отсутствует",
                        "reason", "Указанный пользователь не найден",
                        "status", HttpStatus.NOT_FOUND.name(),
                        "timeStamp", LocalDateTime.now())));
        Event event = eventUserRepository.save(eventMapper.toEvent(newEventDto, user, category));
        return new ResponseEntity<>(eventMapper.toEventFullDto(event), HttpStatus.CREATED);
    }

    @Override
    public EventFullDto getEventById(int userId, int eventId) {
        Event event = eventUserRepository.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                "message",String.format("Событие с id = %d у пользователя с id = %d отсутствует", eventId, userId),
                "reason", "Указанное событие не найдено",
                "status", HttpStatus.NOT_FOUND.name(),
                "timeStamp", LocalDateTime.now())));
        return eventMapper.toEventFullDto(event);
    }

    @Transactional
    @Override
    public EventFullDto updateEventById(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        if (updateEventUserRequest.getStateAction() != null
                && (updateEventUserRequest.getStateAction().equals(StateAction.PUBLISH_EVENT)
                || updateEventUserRequest.getStateAction().equals(StateAction.REJECT_EVENT))) {
            throw new NoAccessException(Map.of("errors", "",
                    "message", "Нет прав на публикацию или её отмену",
                    "reason", "Действия доступны только администратору",
                    "status", HttpStatus.UNAUTHORIZED.name(),
                    "timeStamp", LocalDateTime.now()));
        }
        Event event = eventUserRepository.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                        "message", String.format("Событие с id = %d у пользователя с id = %d отсутствует", eventId, userId),
                        "reason", "Указанное событие не найдено",
                        "status", HttpStatus.NOT_FOUND.name(),
                        "timeStamp", LocalDateTime.now())));
        if (event.getState().equals(State.PUBLISHED)) {
            throw new UnsupportedStatusException(Map.of("errors", "",
                    "message", "невозможно изменить уже опубликованное событие",
                    "reason", "Условия запроса не удовлетворяют требованиям",
                    "status", HttpStatus.CONFLICT.name(),
                    "timeStamp", LocalDateTime.now()));
        }
        Category category = null;
        if (updateEventUserRequest.getCategory() != null) {
            category = categoryRepository.findById(updateEventUserRequest.getCategory())
                    .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                            "message", "Категория с id = " + updateEventUserRequest.getCategory() + " отсутствует",
                            "reason", "Указанная категория не найдена",
                            "status", HttpStatus.NOT_FOUND.name(),
                            "timeStamp", LocalDateTime.now())));
        }
        event.setState(
                updateEventUserRequest.getStateAction().equals(StateAction.SEND_TO_REVIEW) ? State.PENDING : State.CANCELED
        );
        Event updatedEvent = eventMapper.toEvent(updateEventUserRequest, event, category);
        return eventMapper.toEventFullDto(updatedEvent);
    }

    @Override
    public Collection<ParticipationRequestDto> getRequestsByUserId(int userId, int eventId) {
        return requestUserRepository.findByOwnerAndEventsId(userId, eventId).stream()
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult updateRequestByUserId(int userId, int eventId,
                                                                EventRequestStatusUpdateRequest updateRequest) {
        Collection<ParticipationRequest> requests =
                requestUserRepository.findByInitiatorAndEventIdAndRequestsId(userId, eventId, updateRequest.getRequestIds());
        Event event = eventUserRepository.findByInitiatorIdAndId(userId, eventId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                        "message",String.format("Событие с id = %d у пользователя с id = %d отсутствует", eventId, userId),
                        "reason", "Указанное событие не найдено",
                        "status", HttpStatus.NOT_FOUND.name(),
                        "timeStamp", LocalDateTime.now())));
        int limit = event.getParticipantLimit();
        int confirmedRequests = event.getConfirmedRequests();
        int consideringRequests = updateRequest.getRequestIds().size();
        if (confirmedRequests + consideringRequests > limit && limit != 0) {
            requests.stream().peek(request -> request.setStatus(RequestStatus.REJECTED));
            throw new ReachedLimitException(Map.of("errors", "",
                    "message", "Достигнут лимит участников",
                    "reason", "Условия запроса не удовлетворяют требованиям",
                    "status", HttpStatus.CONFLICT.name(),
                    "timeStamp", LocalDateTime.now()));
        } else if (!event.isRequestModeration() || updateRequest.getStatus().equals(RequestStatus.CONFIRMED)) {
            requests.stream().peek(request -> request.setStatus(RequestStatus.CONFIRMED));
            event.setConfirmedRequests(event.getConfirmedRequests() + consideringRequests);
        }
        List<ParticipationRequestDto> updatedRequests = requests.stream()
                .peek(request -> request.setStatus(updateRequest.getStatus()))
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
        return updateRequest.getStatus().equals(RequestStatus.CONFIRMED)
                ? requestMapper.toConfirmedResult(1, updatedRequests)
                : requestMapper.toRejectedResult(1, updatedRequests);
    }
}