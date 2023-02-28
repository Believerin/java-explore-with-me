package ru.practicum.mainservice.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.EndPointHitDto;
import ru.practicum.mainservice.event.dto.*;
import ru.practicum.mainservice.event.service.EventService;
import ru.practicum.statsserviceclient.client.Client;

import java.net.Inet4Address;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events")
@ComponentScan(basePackages = "ru.practicum.statsserviceclient")
public class EventController {

    private final EventService eventService;
    private final Client client;

    @GetMapping
    public Collection<EventShortDto> get(Inet4Address address,
                                         @RequestParam (required = false) String text,
                                         @RequestParam (required = false) Integer[] categories,
                                         @RequestParam (required = false) boolean paid,
                                         @RequestParam (required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                         @RequestParam (required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                         @RequestParam (required = false) boolean onlyAvailable,
                                         @RequestParam (defaultValue = "EVENT_DATE") String sort,
                                         @RequestParam (defaultValue = "0") int from,
                                         @RequestParam (defaultValue = "10") int size) {
        Collection<EventShortDto> eventShortsDto = eventService.get(text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        client.post(new EndPointHitDto("ewm-main-service", "/events", address.getHostAddress(), LocalDateTime.now()));
        return eventShortsDto;
    }

    @GetMapping("/{id}")
    public EventFullDto getById(Inet4Address address, @PathVariable int id) {
        EventFullDto eventFullDto = eventService.getById(id);
        client.post(new EndPointHitDto("ewm-main-service", "/events/" + id, address.getHostAddress(), LocalDateTime.now()));
        return eventFullDto;
    }
}