package ru.practicum.mainservice.event.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.event.EventMapper;
import ru.practicum.mainservice.event.dto.*;
import ru.practicum.mainservice.event.model.*;
import ru.practicum.mainservice.event.repository.EventRepository;
import ru.practicum.mainservice.event.status.State;
import ru.practicum.mainservice.exception.NoSuchBodyException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Collection<EventShortDto> get(String text, Integer[] categories, Boolean paid,
                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                         Boolean onlyAvailable, String sort, int from, int size) {
        String sortProperty = sort.equals("EVENT_DATE") ? "eventDate" : "views";
        Sort sortByDate = Sort.by(Sort.Direction.DESC, sortProperty);
        Pageable pageable = PageRequest.of(from, size, sortByDate);
        QEvent event = QEvent.event;
        List<BooleanExpression> conditions = new ArrayList<>();
        if (text != null) {
            conditions.add(event.annotation.toLowerCase().like("%" + text.toLowerCase() + "%")
                    .or(event.description.toLowerCase().like("%" + text.toLowerCase() + "%")));
        }
        if (categories != null) {
            conditions.add(event.category.id.in(List.of(categories)));
        }
        if (paid != null) {
            conditions.add(event.paid.eq(paid));
        }
        if (rangeStart == null && rangeEnd == null) {
            conditions.add(event.eventDate.gt(LocalDateTime.now()));
        } else {
            conditions.add(event.eventDate.between(rangeStart, rangeEnd));
        }
        if (onlyAvailable != null) {
            if (onlyAvailable) {
                conditions.add((event.participantLimit.gt(event.confirmedRequests)));
            }
        }
        conditions.add(event.state.eq(State.PUBLISHED));
        BooleanExpression accumulatedCondition = conditions.stream()
                .reduce(BooleanExpression::and)
                .get();
        Iterable<Event> events = eventRepository.findAll(accumulatedCondition, pageable);
        List<EventShortDto> eventShortsDto = new ArrayList<>();
        events.forEach(e -> eventShortsDto.add(eventMapper.toEventShortDto(e)));
        return eventShortsDto;
    }

    @Transactional
    @Override
    public EventFullDto getById(int id) {
        Event event = eventRepository.findByIdAndStateEquals(id, State.PUBLISHED)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                "message", "Событие с id = " +  id + " отсутствует либо не опублиовано",
                "reason", "Указанное событие не найдено",
                "status", HttpStatus.NOT_FOUND.name(),
                "timeStamp", LocalDateTime.now())));
        int count = event.getViews();
        event.setViews(++count);
        return eventMapper.toEventFullDto(event);
    }
}