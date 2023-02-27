package ru.practicum.mainservice.category.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewCategoryDto {
    @NotBlank(message = "Поле \"name\" не должно быть пустым")
    private String name;
}