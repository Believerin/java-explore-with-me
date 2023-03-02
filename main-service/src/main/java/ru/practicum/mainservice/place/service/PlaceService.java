package ru.practicum.mainservice.place.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.place.dto.NewPlaceDto;
import ru.practicum.mainservice.place.dto.PlaceDto;

import java.util.Collection;

public interface PlaceService {

    ResponseEntity<PlaceDto> add(NewPlaceDto newPlaceDto);

    Collection<PlaceDto> getByIds(Collection<Integer>  ids);

    Collection<EventFullDto> getEventsByPlace(int id);
}