package ru.practicum.mainservice.place.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.event.EventMapper;
import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.event.model.Event;
import ru.practicum.mainservice.event.repository.EventAdminRepository;
import ru.practicum.mainservice.exception.NoSuchBodyException;
import ru.practicum.mainservice.place.PlaceMapper;
import ru.practicum.mainservice.place.dto.*;
import ru.practicum.mainservice.place.model.Place;
import ru.practicum.mainservice.place.repository.PlaceRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceServiceImpl implements PlaceService {

    private final PlaceRepository placeRepository;
    private final PlaceMapper placeMapper;
    private final EventAdminRepository eventAdminRepository;
    private final EventMapper eventMapper;

    @Transactional
    @Override
    public ResponseEntity<PlaceDto> add(NewPlaceDto newPlaceDto) {
        Place place = placeRepository.save(placeMapper.toPlace(newPlaceDto));
        return new ResponseEntity<>(placeMapper.toPlaceDto(place), HttpStatus.CREATED);
    }

    @Override
    public Collection<PlaceDto> getByIds(Collection<Integer> ids) {
        Collection<Place> places = placeRepository.findAllById(ids);
        return places.stream().map(placeMapper::toPlaceDto).collect(Collectors.toList());
    }

    @Override
    public Collection<EventFullDto> getEventsByPlace(int id) {
        placeRepository.findById(id)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                        "message", "Локация с id = " + id + " отсутствует",
                        "reason", "Указанная локация не найдена",
                        "status", HttpStatus.NOT_FOUND.name(),
                        "timeStamp", LocalDateTime.now())));
        Collection<Event> events = eventAdminRepository.findAllByPlace(id);
        return events.stream().map(eventMapper::toEventFullDto).collect(Collectors.toList());
    }
}