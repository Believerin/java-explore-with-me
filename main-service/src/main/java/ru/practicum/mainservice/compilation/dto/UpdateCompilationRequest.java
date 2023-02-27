package ru.practicum.mainservice.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateCompilationRequest {
    private List<Integer> events;
    private boolean pinned;
    @NotBlank(message = "Поле \"title\" не должно быть пустым")
    private String title;
}