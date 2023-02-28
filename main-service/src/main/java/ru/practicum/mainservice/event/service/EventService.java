package ru.practicum.mainservice.event.service;

import ru.practicum.mainservice.event.dto.*;

import java.time.LocalDateTime;
import java.util.Collection;

public interface EventService {
    Collection<EventShortDto> get(String text, Integer[] categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                  Boolean onlyAvailable, String sort, int from, int size);

    EventFullDto getById(int id);
}