package ru.practicum.statsserviceserver.service;

import ru.practicum.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface EndPointHitService {
    void post(EndPointHitDto endPointHitDto);

    List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}