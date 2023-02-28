package ru.practicum.mainservice.event.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.repository.CategoryRepository;
import ru.practicum.mainservice.event.EventMapper;
import ru.practicum.mainservice.event.dto.*;
import ru.practicum.mainservice.event.model.Event;
import ru.practicum.mainservice.event.model.QEvent;
import ru.practicum.mainservice.event.repository.EventAdminRepository;
import ru.practicum.mainservice.event.status.State;
import ru.practicum.mainservice.event.status.StateAction;
import ru.practicum.mainservice.exception.NoSuchBodyException;
import ru.practicum.mainservice.exception.UnsupportedStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventAdminServiceImpl implements EventAdminService {

    private final EventAdminRepository eventAdminRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    @Override
    public Collection<EventFullDto> get(Integer[] users, String[] states, Integer[] categories,
                                        LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                        int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();
        if (users != null) {
            conditions.add(event.initiator.id.in(List.of(users)));
        }
        if (states != null) {
            conditions.add(event.state.in(Stream.of(states).map(State::valueOf).collect(Collectors.toList())));
        }
        if (categories != null) {
            conditions.add(event.category.id.in(List.of(categories)));
        }
        if (rangeStart == null && rangeEnd == null) {
            conditions.add(event.eventDate.gt(LocalDateTime.now()));
        } else {
            conditions.add(event.eventDate.between(rangeStart, rangeEnd));
        }
        BooleanExpression accumulatedCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();
        Iterable<Event> events = eventAdminRepository.findAll(accumulatedCondition, pageable);
        List<EventFullDto> eventFullsDto = new ArrayList<>();
        events.forEach(e -> eventFullsDto.add(eventMapper.toEventFullDto(e)));
        return eventFullsDto;
    }

    @Transactional
    @Override
    public EventFullDto update(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventAdminRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                "message", "Событие с id = " + eventId + " отсутствует",
                "reason", "Указанное событие не найдено",
                "status", HttpStatus.NOT_FOUND.name(),
                "timeStamp", LocalDateTime.now())));
        if (!event.getState().equals(State.PENDING)) {
            throw new UnsupportedStatusException(Map.of("errors", "",
                    "message", updateEventAdminRequest.getStateAction().equals(StateAction.PUBLISH_EVENT)
                            ? "невозможно опубликовать опубликованное/отменённое событие"
                            : "невозможно отменить опубликованное событие",
                    "reason", "Условия запроса не удовлетворяют требованиям",
                    "status", HttpStatus.CONFLICT.name(),
                    "timeStamp", LocalDateTime.now()));
        } else {
            if (updateEventAdminRequest.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else {
                event.setState(State.CANCELED);
            }
        }
        Category category = null;
        if (updateEventAdminRequest.getCategory() != null) {
            category = categoryRepository.findById(updateEventAdminRequest.getCategory())
                    .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                            "message", "Категория с id = " + updateEventAdminRequest.getCategory() + " отсутствует",
                            "reason", "Указанная категория не найдена",
                            "status", HttpStatus.NOT_FOUND.name(),
                            "timeStamp", LocalDateTime.now())));
        }
        Event updatedEvent = eventMapper.toEvent(updateEventAdminRequest, event, category);
        return eventMapper.toEventFullDto(updatedEvent);
    }
}