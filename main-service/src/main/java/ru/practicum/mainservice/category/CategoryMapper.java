package ru.practicum.mainservice.category;

import org.mapstruct.*;
import ru.practicum.mainservice.category.dto.*;
import ru.practicum.mainservice.category.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    Category toCategory(NewCategoryDto source);

    CategoryDto toCategoryDto(Category destination);
}