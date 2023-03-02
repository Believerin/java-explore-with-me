package ru.practicum.mainservice.event.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ru.practicum.mainservice.event.model.Event;

import java.util.Collection;
import java.util.List;

public interface EventAdminRepository extends JpaRepository<Event, Integer>, QuerydslPredicateExecutor<Event> {

    List<Event> findByIdIn(List<Integer> events);

    @Query(value = "select e " +
                   "from Event e " +
                   "where distance((select p.lat from Place p where p.id = ?1)," +
                   "(select pq.lon from Place pq where pq.id = ?1)," +
                   "e.location.lat, e.location.lon)/2 < (select ps.radius from Place ps where ps.id = ?1)")
    Collection<Event> findAllByPlace(int id);
}