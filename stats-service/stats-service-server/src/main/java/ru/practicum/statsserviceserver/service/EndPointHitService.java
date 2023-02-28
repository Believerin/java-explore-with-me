package ru.practicum.statsserviceserver.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EndPointHitService {
    ResponseEntity<Object> post(EndPointHitDto endPointHitDto);

    List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}