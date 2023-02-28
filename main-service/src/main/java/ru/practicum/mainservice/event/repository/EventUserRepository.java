package ru.practicum.mainservice.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.event.model.Event;

import java.util.Optional;

public interface EventUserRepository extends JpaRepository<Event, Integer> {
    Page<Event> findAllByInitiatorId(int userId, Pageable pageable);

    Optional<Event> findByInitiatorIdAndId(int userId, int eventId);
}