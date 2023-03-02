package ru.practicum.mainservice.place.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.mainservice.place.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Integer> {
}
