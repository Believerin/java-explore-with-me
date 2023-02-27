package ru.practicum.mainservice.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.compilation.dto.CompilationDto;
import ru.practicum.mainservice.compilation.service.CompilationService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compilations")
public class CompilationController {

    private final CompilationService compilationService;

    @GetMapping
    public Collection<CompilationDto> get(@RequestParam (required = false) boolean pinned,
                                          @RequestParam (defaultValue = "0") int from,
                                          @RequestParam (defaultValue = "10") int size) {
        return compilationService.get(pinned, from, size);
    }

    @GetMapping("/{compId}")
    public CompilationDto getById(@PathVariable int compId) {
        return compilationService.getById(compId);
    }
}