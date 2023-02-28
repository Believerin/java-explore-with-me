package ru.practicum.mainservice.compilation.dto;

import lombok.*;
import ru.practicum.mainservice.event.dto.EventShortDto;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompilationDto {
    private int id;
    private List<EventShortDto> events;
    private boolean pinned;
    private String title;
}