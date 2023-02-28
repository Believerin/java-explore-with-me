package ru.practicum.mainservice.compilation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.compilation.model.Compilation;

public interface CompilationAdminRepository extends JpaRepository<Compilation, Integer> {
}