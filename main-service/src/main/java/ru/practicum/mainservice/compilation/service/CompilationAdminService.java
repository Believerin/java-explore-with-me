package ru.practicum.mainservice.compilation.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.mainservice.compilation.dto.*;

public interface CompilationAdminService {
    ResponseEntity<CompilationDto> add(NewCompilationDto newCompilationDto);

    CompilationDto update(int compId, UpdateCompilationRequest request);

    ResponseEntity<Object> delete(int compId);
}