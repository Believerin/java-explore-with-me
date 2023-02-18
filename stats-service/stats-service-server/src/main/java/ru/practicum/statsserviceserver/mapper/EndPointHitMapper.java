package ru.practicum.statsserviceserver.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.EndPointHitDto;
import ru.practicum.statsserviceserver.model.EndPointHit;

@Mapper(componentModel = "spring")
public interface EndPointHitMapper {
    EndPointHit toEndPointHit(EndPointHitDto source);

    EndPointHitDto toEndPointHitDto(EndPointHit destination);
}