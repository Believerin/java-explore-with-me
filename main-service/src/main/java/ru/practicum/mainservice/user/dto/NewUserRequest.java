package ru.practicum.mainservice.user.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewUserRequest {
    @Email
    private String email;
    @NotBlank(message = "Поле \"name\" не должно быть пустым")
    private String name;
}