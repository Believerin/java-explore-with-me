package ru.practicum.statsserviceserver.service;

import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.statsserviceserver.exception.ValidationException;
import ru.practicum.statsserviceserver.mapper.*;
import ru.practicum.statsserviceserver.model.*;
import ru.practicum.statsserviceserver.repository.EndPointHitRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EndPointHitServiceImpl implements EndPointHitService {

    private final EndPointHitRepository repository;
    private final EndPointHitMapper endPointHitMapper;
    private final ViewStatsMapper viewStatsMapper;

    @Transactional
    @Override
    public void post(EndPointHitDto endPointHitDto) {
        EndPointHit endPointHit = endPointHitMapper.toEndPointHit(endPointHitDto);
        repository.save(endPointHit);
    }

    @Override
    public ViewStatsDto get(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new ValidationException("нижняя граница времени позже верхней");
        }
        ViewStats viewStats;
        if (!unique) {
            viewStats = repository.findAllByParameters(start, end, uris, ViewStats.class);
        } else {
            viewStats = repository.findAllUniqueIpByParameters(start, end, uris, ViewStats.class);
        }
        return viewStatsMapper.toViewStatsDto(viewStats);
    }
}