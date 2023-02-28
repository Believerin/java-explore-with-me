package ru.practicum.mainservice.event.service;

import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.event.dto.UpdateEventAdminRequest;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventAdminService {
    Collection<EventFullDto> get(Integer[] users, String[] states, Integer[] categories,
                                 LocalDateTime rangeStart, LocalDateTime rangeEnd, int from, int size);

    EventFullDto update(int eventId, UpdateEventAdminRequest request);
}
