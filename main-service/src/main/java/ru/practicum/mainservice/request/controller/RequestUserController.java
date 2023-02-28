package ru.practicum.mainservice.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;
import ru.practicum.mainservice.request.service.RequestUserService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class RequestUserController {

    private final RequestUserService requestUserService;

    @GetMapping
    public Collection<ParticipationRequestDto> get(@PathVariable int userId) {
        return requestUserService.get(userId);
    }

    @PostMapping
    public ResponseEntity<ParticipationRequestDto> add(@PathVariable int userId, @RequestParam int eventId) {
        return requestUserService.add(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ParticipationRequestDto update(@PathVariable int userId, @PathVariable int requestId) {
        return requestUserService.update(userId, requestId);
    }
}