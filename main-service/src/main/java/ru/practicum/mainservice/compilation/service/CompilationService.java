package ru.practicum.mainservice.compilation.service;

import ru.practicum.mainservice.compilation.dto.CompilationDto;

import java.util.Collection;

public interface CompilationService {
    Collection<CompilationDto> get(boolean pinned, int from, int size);

    CompilationDto getById(int compId);
}
