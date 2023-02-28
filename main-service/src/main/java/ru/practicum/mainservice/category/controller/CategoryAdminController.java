package ru.practicum.mainservice.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.category.dto.*;
import ru.practicum.mainservice.category.service.CategoryAdminService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/categories")
public class CategoryAdminController {

    private final CategoryAdminService categoryAdminService;

    @PostMapping
    public ResponseEntity<CategoryDto> add(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        return categoryAdminService.add(newCategoryDto);
    }

    @DeleteMapping("/{catId}")
    public ResponseEntity<Object> delete(@PathVariable int catId) {
        return categoryAdminService.delete(catId);
    }

    @PatchMapping("/{catId}")
    public ResponseEntity<CategoryDto> update(@PathVariable int catId, @Valid @RequestBody CategoryDto categoryDto) {
        return categoryAdminService.update(catId, categoryDto);
    }
}