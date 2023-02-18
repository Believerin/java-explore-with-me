package ru.practicum.statsserviceserver.mapper;

import org.mapstruct.Mapper;
import ru.practicum.dto.ViewStatsDto;
import ru.practicum.statsserviceserver.model.ViewStats;

@Mapper(componentModel = "spring")
public interface ViewStatsMapper {
    ViewStats toViewStats(ViewStatsDto source);

    ViewStatsDto toViewStatsDto(ViewStats destination);
}