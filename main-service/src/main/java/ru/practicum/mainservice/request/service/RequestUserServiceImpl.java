package ru.practicum.mainservice.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.event.model.Event;
import ru.practicum.mainservice.event.repository.EventUserRepository;
import ru.practicum.mainservice.event.status.State;
import ru.practicum.mainservice.exception.*;
import ru.practicum.mainservice.request.RequestMapper;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;
import ru.practicum.mainservice.request.model.ParticipationRequest;
import ru.practicum.mainservice.request.repository.RequestUserRepository;
import ru.practicum.mainservice.request.status.RequestStatus;
import ru.practicum.mainservice.user.model.User;
import ru.practicum.mainservice.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RequestUserServiceImpl implements RequestUserService {

    private final RequestUserRepository requestUserRepository;
    private final RequestMapper requestMapper;
    private final UserRepository userRepository;
    private final EventUserRepository eventUserRepository;

    @Override
    public Collection<ParticipationRequestDto> get(int userId) {
        userRepository.findById(userId).orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                     "message","Пользователь с id = " + userId + " отсутствует",
                     "reason", "Указанный пользователь не найден",
                     "status", HttpStatus.NOT_FOUND.name(),
                     "timeStamp", LocalDateTime.now())));
        Collection<ParticipationRequest> participationRequests = requestUserRepository.findByRequesterId(userId);
        return participationRequests.stream().map(requestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public ResponseEntity<ParticipationRequestDto> add(int userId, int eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
             "message", "Пользователь с id = " + userId + " отсутствует",
             "reason","Указанный пользователь не найден",
             "status", HttpStatus.NOT_FOUND.name(),
             "timeStamp", LocalDateTime.now())));
        Event event = eventUserRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                      "message", "Событие с id = " + eventId + " отсутствует",
                      "reason", "Указанное событие не найдено",
                      "status", HttpStatus.NOT_FOUND.name(),
                      "timeStamp", LocalDateTime.now())));
        if (event.getState().equals(State.PENDING) || event.getState().equals(State.CANCELED)) {
            throw new InvalidRequestException(Map.of("errors", "",
                    "message","Попытка присоединиться к неопубликованному событию",
                    "reason", "Указанное событие недоступно дял присоединения",
                    "status", HttpStatus.CONFLICT.name(),
                    "timeStamp", LocalDateTime.now()));
        }
        if (event.getInitiator().getId() == userId) {
            throw new InvalidRequestException(Map.of("errors", "",
                    "message","Попытка присоединиться к своему событию",
                    "reason", "Указанное событие недоступно дял присоединения",
                    "status", HttpStatus.CONFLICT.name(),
                    "timeStamp", LocalDateTime.now()));
        }
        if (event.getParticipantLimit() == event.getConfirmedRequests()) {
            throw new ReachedLimitException(Map.of("errors", "",
                    "message","Достигнут лимит участников",
                    "reason", "Условия запроса не удовлетворяют требованиям",
                    "status", HttpStatus.CONFLICT.name(),
                    "timeStamp", LocalDateTime.now()));
        }
        ParticipationRequest request;
        request = requestUserRepository.save(
                ParticipationRequest.builder()
                        .requester(user)
                        .status(event.isRequestModeration() ? RequestStatus.PENDING : RequestStatus.CONFIRMED)
                        .event(event)
                        .build());
        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            int count = event.getConfirmedRequests();
            event.setConfirmedRequests(++count);
        }
        return new ResponseEntity<>(requestMapper.toParticipationRequestDto(request), HttpStatus.CREATED);
    }

    @Transactional
    @Override
    public ParticipationRequestDto update(int userId, int requestId) {
        ParticipationRequest participationRequest = requestUserRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                "message",String.format("Запрос с id = %d от пользователя с id = %d отсутствует", requestId, userId),
                "reason", "Указанный запрос не найден",
                "status", HttpStatus.NOT_FOUND.name(),
                "timeStamp", LocalDateTime.now())));
        participationRequest.setStatus(RequestStatus.CANCELED);
        return requestMapper.toParticipationRequestDto(participationRequest);
    }
}