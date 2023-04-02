package ru.practicum.mainservice.place.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.event.dto.EventFullDto;
import ru.practicum.mainservice.place.dto.NewPlaceDto;
import ru.practicum.mainservice.place.dto.PlaceDto;
import ru.practicum.mainservice.place.service.PlaceService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/places")
public class PlaceController {

    private final PlaceService placeService;

    @PostMapping
    public ResponseEntity<PlaceDto> add(@RequestBody NewPlaceDto newPlaceDto) {
        return placeService.add(newPlaceDto);
    }

    @GetMapping
    public Collection<PlaceDto> getByIds(@RequestParam Collection<Integer> ids) {
        return placeService.getByIds(ids);
    }

    @GetMapping("/{id}/events")
    public Collection<EventFullDto> getEventsByPlaceId(@PathVariable int id) {
        return placeService.getEventsByPlace(id);
    }
}