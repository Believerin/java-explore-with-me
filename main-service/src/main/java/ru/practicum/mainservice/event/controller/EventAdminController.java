package ru.practicum.mainservice.event.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.event.dto.*;
import ru.practicum.mainservice.event.service.EventAdminService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class EventAdminController {

    private final EventAdminService eventAdminService;

    @GetMapping
    public Collection<EventFullDto> get(@RequestParam (required = false) Integer[] users,
                                        @RequestParam (required = false) String[] states,
                                        @RequestParam (required = false) Integer[] categories,
                                        @RequestParam (required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                        @RequestParam (required = false)
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                        @RequestParam (defaultValue = "0") int from,
                                        @RequestParam (defaultValue = "10") int size) {
        return eventAdminService.get(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto update(@PathVariable int eventId, @Valid @RequestBody UpdateEventAdminRequest request) {
        return eventAdminService.update(eventId, request);
    }
}