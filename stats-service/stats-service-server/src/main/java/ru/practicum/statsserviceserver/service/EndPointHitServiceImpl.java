package ru.practicum.statsserviceserver.service;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.statsserviceserver.exception.ValidationException;
import ru.practicum.statsserviceserver.mapper.*;
import ru.practicum.statsserviceserver.model.*;
import ru.practicum.statsserviceserver.repository.EndPointHitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EndPointHitServiceImpl implements EndPointHitService {

    private final EndPointHitRepository repository;
    private final EndPointHitMapper endPointHitMapper;
    private final ViewStatsMapper viewStatsMapper;

    @Transactional
    @Override
    public ResponseEntity<Object> post(EndPointHitDto endPointHitDto) {
        EndPointHit endPointHit = endPointHitMapper.toEndPointHit(endPointHitDto);
        repository.save(endPointHit);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public List<ViewStatsDto> get(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        if (start.isAfter(end)) {
            throw new ValidationException("нижняя граница времени позже верхней");
        }
        List<ViewStats> viewStats;
        if (!unique) {
            viewStats = repository.findAllByParameters(start, end, uris, ViewStats.class);
        } else {
            viewStats = repository.findAllUniqueIpByParameters(start, end, uris, ViewStats.class);
        }
        return viewStats.stream().map(viewStatsMapper::toViewStatsDto).collect(Collectors.toList());
    }
}