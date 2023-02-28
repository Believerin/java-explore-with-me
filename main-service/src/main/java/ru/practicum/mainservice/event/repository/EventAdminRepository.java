package ru.practicum.mainservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.mainservice.event.model.Event;

import java.util.List;

public interface EventAdminRepository extends JpaRepository<Event, Integer>, QuerydslPredicateExecutor<Event> {

    List<Event> findByIdIn(List<Integer> events);
}