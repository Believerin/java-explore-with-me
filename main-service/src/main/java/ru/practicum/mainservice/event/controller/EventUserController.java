package ru.practicum.mainservice.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.event.dto.*;
import ru.practicum.mainservice.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.mainservice.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;
import ru.practicum.mainservice.event.dto.UpdateEventUserRequest;
import ru.practicum.mainservice.event.service.EventUserService;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class EventUserController {

    private final EventUserService eventUserService;

    @GetMapping
    public Collection<EventShortDto> get(@PathVariable int userId,
                                         @RequestParam (defaultValue = "0") int from,
                                         @RequestParam (defaultValue = "10") int size) {
        return eventUserService.getEvents(userId, from, size);
    }

    @PostMapping
    public ResponseEntity<EventFullDto> add(@PathVariable int userId, @RequestBody @Valid NewEventDto newEventDto) {
        return eventUserService.addEvent(userId, newEventDto);
    }

    @GetMapping("/{eventId}")
    public EventFullDto getById(@PathVariable int userId, @PathVariable int eventId) {
        return eventUserService.getEventById(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateById(@PathVariable int userId, @PathVariable int eventId,
                                   @RequestBody @Valid UpdateEventUserRequest request) {
        return eventUserService.updateEventById(userId, eventId, request);
    }

    @GetMapping("/{eventId}/requests")
    public Collection<ParticipationRequestDto> getRequestsByUserId(@PathVariable int userId, @PathVariable int eventId) {
        return eventUserService.getRequestsByUserId(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestByUserId(@PathVariable int userId, @PathVariable int eventId,
                                                                @RequestBody EventRequestStatusUpdateRequest request) {
        return eventUserService.updateRequestByUserId(userId, eventId, request);
    }
}