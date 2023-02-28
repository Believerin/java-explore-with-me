package ru.practicum.mainservice.category.service;

import org.springframework.http.ResponseEntity;
import ru.practicum.mainservice.category.dto.*;

public interface CategoryAdminService {
    ResponseEntity<CategoryDto> add(NewCategoryDto newCategoryDto);

    ResponseEntity<Object> delete(int catId);

    ResponseEntity<CategoryDto> update(int catId, CategoryDto categoryDto);
}