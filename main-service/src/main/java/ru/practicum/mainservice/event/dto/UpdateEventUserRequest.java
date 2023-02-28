package ru.practicum.mainservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.mainservice.event.model.Location;
import ru.practicum.mainservice.event.valid.StartInGivenHoursValid;
import ru.practicum.mainservice.event.status.StateAction;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateEventUserRequest {
    private String annotation;
    private Integer category;
    private String description;
    @StartInGivenHoursValid(hours = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    private String title;
}