package ru.practicum.mainservice.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.compilation.CompilationMapper;
import ru.practicum.mainservice.compilation.dto.*;
import ru.practicum.mainservice.compilation.model.Compilation;
import ru.practicum.mainservice.compilation.repository.CompilationAdminRepository;
import ru.practicum.mainservice.event.model.Event;
import ru.practicum.mainservice.event.repository.EventAdminRepository;
import ru.practicum.mainservice.exception.NoSuchBodyException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationAdminServiceImpl implements CompilationAdminService {

    private final CompilationAdminRepository compilationAdminRepository;
    private final EventAdminRepository eventAdminRepository;
    private final CompilationMapper compilationMapper;

    @Transactional
    @Override
    public ResponseEntity<CompilationDto> add(NewCompilationDto newCompilationDto) {
        List<Event> events = eventAdminRepository.findByIdIn(newCompilationDto.getEvents());
        Compilation compilation;
        compilation = compilationAdminRepository.save(compilationMapper.toCompilation(1, newCompilationDto, events));
        return new ResponseEntity<>(compilationMapper.toCompilationDto(compilation), HttpStatus.CREATED);
    }

    @Transactional
    @Override
    public CompilationDto update(int compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation compilation = compilationAdminRepository.findById(compId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                "message", "Подборка с id = " + compId + " отсутствует",
                "reason", "Указанная подборка не найдена",
                "status", HttpStatus.NOT_FOUND.name(),
                "timeStamp", LocalDateTime.now())));
        List<Event> events = null;
        if (updateCompilationRequest.getEvents() != null) {
            events = eventAdminRepository.findByIdIn(updateCompilationRequest.getEvents());
        }
        Compilation updatedCompilation = compilationMapper.toCompilation(1, updateCompilationRequest, compilation, events);
        return compilationMapper.toCompilationDto(updatedCompilation);
    }

    @Transactional
    @Override
    public ResponseEntity<Object> delete(int compId) {
        compilationAdminRepository.findById(compId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                        "message", "Подборка с id = " + compId + " отсутствует",
                        "reason", "Указанная подборка не найдена",
                        "status", HttpStatus.NOT_FOUND.name(),
                        "timeStamp", LocalDateTime.now())));
        compilationAdminRepository.deleteById(compId);
        return ResponseEntity.status(204).build();
    }
}