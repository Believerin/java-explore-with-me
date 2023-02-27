package ru.practicum.mainservice.event.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.event.dto.EventShortDto;
import ru.practicum.mainservice.event.dto.NewEventDto;
import ru.practicum.mainservice.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;
import ru.practicum.mainservice.event.dto.UpdateEventUserRequest;

import java.util.Collection;

public interface EventUserService {
    Collection<EventShortDto> getEvents(int userId, int from, int size);

    ResponseEntity<EventFullDto> addEvent(int userId, NewEventDto newEventDto);

    EventFullDto getEventById(int userId, int eventId);

    EventFullDto updateEventById(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest);

    Collection<ParticipationRequestDto> getRequestsByUserId(int userId, int eventId);

    EventRequestStatusUpdateResult updateRequestByUserId(int userId, int eventId,
                                                         EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);
}