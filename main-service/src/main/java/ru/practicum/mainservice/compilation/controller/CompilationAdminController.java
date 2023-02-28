package ru.practicum.mainservice.compilation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.compilation.dto.*;
import ru.practicum.mainservice.compilation.service.CompilationAdminService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/compilations")
public class CompilationAdminController {

    private final CompilationAdminService compilationAdminService;

    @PostMapping
    public ResponseEntity<CompilationDto> add(@RequestBody @Valid NewCompilationDto newCompilationDto) {
        return compilationAdminService.add(newCompilationDto);
    }

    @PatchMapping("/{compId}")
    public CompilationDto update(@PathVariable int compId, @RequestBody UpdateCompilationRequest request) {
        return compilationAdminService.update(compId, request);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> delete(@PathVariable int compId) {
        return compilationAdminService.delete(compId);
    }
}