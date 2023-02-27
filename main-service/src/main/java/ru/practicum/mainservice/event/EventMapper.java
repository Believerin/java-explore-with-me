package ru.practicum.mainservice.event;

import org.mapstruct.*;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.event.dto.*;
import ru.practicum.mainservice.event.model.Event;
import ru.practicum.mainservice.user.model.User;

@Mapper(componentModel = "spring")
public interface EventMapper {
    Event toEvent(EventFullDto source);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "category", target = "category")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event toEvent(UpdateEventUserRequest source, @MappingTarget Event event, Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "category", target = "category")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Event toEvent(UpdateEventAdminRequest source, @MappingTarget Event event, Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "category", target = "category")
    @Mapping(target = "state", constant = "PENDING")
    @Mapping(source = "user", target = "initiator")
    Event toEvent(NewEventDto source, User user, Category category);

    EventFullDto toEventFullDto(Event destination);

    EventShortDto toEventShortDto(Event destination);
}