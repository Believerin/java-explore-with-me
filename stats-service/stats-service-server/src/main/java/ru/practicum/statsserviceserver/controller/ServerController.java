package ru.practicum.statsserviceserver.controller;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.*;
import ru.practicum.statsserviceserver.service.EndPointHitService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ServerController {

    private final EndPointHitService endPointHitService;

    @PostMapping("/hit")
    public ResponseEntity<Object> post(@RequestBody @Valid EndPointHitDto endPointHitDto) {
        return endPointHitService.post(endPointHitDto);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> get(HttpServletRequest request, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                  @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                  @RequestParam String[] uris,
                                  @RequestParam (defaultValue = "false") boolean unique) {
        return endPointHitService.get(start, end, uris, unique);
    }
}