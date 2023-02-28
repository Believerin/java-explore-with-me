package ru.practicum.mainservice.request.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;

import java.util.Collection;

public interface RequestUserService {
    Collection<ParticipationRequestDto> get(int userId);

    ResponseEntity<ParticipationRequestDto> add(int userId, int eventId);

    ParticipationRequestDto update(int userId, int requestId);
}