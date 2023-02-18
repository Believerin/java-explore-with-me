package ru.practicum.statsserviceserver.controller;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.statsserviceserver.service.EndPointHitService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ServerController {

    private final EndPointHitService endPointHitService;

    @PostMapping("/hit")
    public void post(@RequestBody @Valid EndPointHitDto endPointHitDto) {
        endPointHitService.post(endPointHitDto);
    }

    @GetMapping("/stats")
    public ViewStatsDto get(HttpServletRequest request, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                            @RequestParam String[] uris,
                                            @RequestParam boolean unique) {
        String t = request.getQueryString();
        return endPointHitService.get(start, end, uris, unique);
    }
}