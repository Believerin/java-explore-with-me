package ru.practicum.mainservice.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.category.model.Category;

public interface CategoryAdminRepository extends JpaRepository<Category, Integer> {
}