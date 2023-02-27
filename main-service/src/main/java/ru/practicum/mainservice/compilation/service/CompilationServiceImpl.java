package ru.practicum.mainservice.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.compilation.CompilationMapper;
import ru.practicum.mainservice.compilation.dto.CompilationDto;
import ru.practicum.mainservice.compilation.model.Compilation;
import ru.practicum.mainservice.compilation.repository.CompilationRepository;
import ru.practicum.mainservice.exception.NoSuchBodyException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    @Override
    public Collection<CompilationDto> get(boolean pinned, int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        Iterable<Compilation> compilations = compilationRepository.findByPinned(pinned, pageable);
        Collection<CompilationDto> compilationsDto = new ArrayList<>();
        compilations.forEach(compilation -> compilationsDto.add(compilationMapper.toCompilationDto(compilation)));
        return compilationsDto;
    }

    @Override
    public CompilationDto getById(int compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                "message", "Подборка с id = " + compId + " отсутствует",
                "reason", "Указанная подборка не найдена",
                "status", HttpStatus.NOT_FOUND.name(),
                "timeStamp", LocalDateTime.now())));
        return compilationMapper.toCompilationDto(compilation);
    }
}