package ru.practicum.mainservice.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.category.CategoryMapper;
import ru.practicum.mainservice.category.dto.*;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.category.repository.CategoryAdminRepository;
import ru.practicum.mainservice.exception.NoSuchBodyException;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryAdminServiceImpl implements CategoryAdminService {

    private final CategoryAdminRepository categoryAdminRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    @Override
    public ResponseEntity<CategoryDto> add(NewCategoryDto newCategoryDto) {
        Category category = categoryAdminRepository.save(categoryMapper.toCategory(newCategoryDto));
        return new ResponseEntity<>(categoryMapper.toCategoryDto(category), HttpStatus.CREATED);
     }

    @Transactional
    @Override
    public ResponseEntity<Object> delete(int catId) {
        categoryAdminRepository.findById(catId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                        "message", "Категория с id = " + catId + " отсутствует",
                        "reason", "Указанная категория не найдена",
                        "status", HttpStatus.NOT_FOUND.name(),
                        "timeStamp", LocalDateTime.now())));
        categoryAdminRepository.deleteById(catId);
        return ResponseEntity.status(204).build();
    }

    @Transactional
    @Override
    public ResponseEntity<CategoryDto> update(int catId, CategoryDto categoryDto) {
        Category category = categoryAdminRepository.findById(catId)
                .orElseThrow(() -> new NoSuchBodyException(Map.of("errors", "",
                        "message", "Категория с id = " + catId + " отсутствует",
                        "reason", "Указанная категория не найдена",
                        "status", HttpStatus.NOT_FOUND.name(),
                        "timeStamp", LocalDateTime.now())));
        category.setName(categoryDto.getName());
        return new ResponseEntity<>(categoryMapper.toCategoryDto(category), HttpStatus.OK);
    }
}