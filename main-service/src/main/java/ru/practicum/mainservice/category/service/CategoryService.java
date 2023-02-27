package ru.practicum.mainservice.category.service;

import ru.practicum.mainservice.category.dto.CategoryDto;

import java.util.Collection;

public interface CategoryService {
    Collection<CategoryDto> get(int from, int size);

    CategoryDto getById(int catId);
}
