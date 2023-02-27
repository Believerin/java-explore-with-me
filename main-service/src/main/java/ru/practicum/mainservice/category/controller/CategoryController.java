package ru.practicum.mainservice.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.category.service.CategoryService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public Collection<CategoryDto> get(@RequestParam (defaultValue = "0") int from,
                                       @RequestParam (defaultValue = "10") int size) {
        return categoryService.get(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getById(@PathVariable int catId) {
        return categoryService.getById(catId);
    }
}