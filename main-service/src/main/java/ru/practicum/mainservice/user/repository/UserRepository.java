package ru.practicum.mainservice.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.user.model.User;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findByIdIn(Collection<Integer> ids, Pageable page);
}