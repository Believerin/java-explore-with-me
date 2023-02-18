package ru.practicum.statsserviceserver.service;

import ru.practicum.dto.*;

import java.time.LocalDateTime;

public interface EndPointHitService {
    void post(EndPointHitDto endPointHitDto);

    ViewStatsDto get(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}