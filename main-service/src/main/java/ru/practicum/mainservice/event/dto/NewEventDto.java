package ru.practicum.mainservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.mainservice.event.model.*;
import ru.practicum.mainservice.event.status.State;
import ru.practicum.mainservice.event.valid.StartInGivenHoursValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewEventDto {
    @NotBlank(message = "Поле \"annotation\" не должно быть пустым")
    private String annotation;
    @NotNull(message = "Поле \"category\" не должно быть пустым")
    private int category;
    @NotBlank(message = "Поле \"description\" не должно быть пустым")
    private String description;
    @StartInGivenHoursValid(hours = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull(message = "Поле \"location\" не должно быть пустым")
    private Location location;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;
    private State state;
    @NotBlank
    private String title;
}