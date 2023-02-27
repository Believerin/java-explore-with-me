package ru.practicum.mainservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.mainservice.event.model.Event;
import ru.practicum.mainservice.event.status.State;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer>, QuerydslPredicateExecutor<Event> {

    Optional<Event> findByIdAndStateEquals(int id, State state);
}