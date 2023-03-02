package ru.practicum.mainservice.place;

import org.mapstruct.Mapper;
import ru.practicum.mainservice.place.dto.NewPlaceDto;
import ru.practicum.mainservice.place.dto.PlaceDto;
import ru.practicum.mainservice.place.model.Place;

@Mapper(componentModel = "spring")
public interface PlaceMapper {

    Place toPlace(NewPlaceDto source);

    PlaceDto toPlaceDto(Place place);
}