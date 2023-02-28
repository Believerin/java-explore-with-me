package ru.practicum.mainservice.compilation;

import org.mapstruct.*;
import ru.practicum.mainservice.compilation.dto.*;
import ru.practicum.mainservice.compilation.model.Compilation;
import ru.practicum.mainservice.event.model.Event;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CompilationMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "source.pinned", target = "pinned", defaultValue = "false")
    @Mapping(source = "events", target = "events")
    Compilation toCompilation(int count, NewCompilationDto source, List<Event> events);

    CompilationDto toCompilationDto(Compilation destination);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "events", target = "events")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Compilation toCompilation(int count, UpdateCompilationRequest source, @MappingTarget Compilation compilation, List<Event> events);
}