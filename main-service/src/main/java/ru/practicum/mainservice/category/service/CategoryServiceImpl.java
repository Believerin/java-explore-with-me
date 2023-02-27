package ru.practicum.mainservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.category.CategoryMapper;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.repository.CategoryRepository;
import ru.practicum.mainservice.exception.NoSuchBodyException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public Collection<CategoryDto> get(int from, int size) {
        Pageable pageable = PageRequest.of(from, size);
        return categoryRepository.findAll(pageable).getContent().stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getById(int catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                "message", "Категория с id = " + catId + " отсутствует",
                "reason", "Указанная категория не найдена",
                "status", HttpStatus.NOT_FOUND.name(),
                "timeStamp", LocalDateTime.now())));
        int r = 6;
        return categoryMapper.toCategoryDto(category);
    }
}
