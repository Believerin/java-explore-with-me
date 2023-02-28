package ru.practicum.mainservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.event.model.*;
import ru.practicum.mainservice.event.status.State;
import ru.practicum.mainservice.user.dto.UserDto;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventFullDto {
    private int id;
    private String annotation;
    private CategoryDto category;
    private int confirmedRequests;
    private LocalDateTime createdOn;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime eventDate;
    private UserDto initiator;
    private Location location;
    private boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    private State state;
    private String title;
    private int views;
}
