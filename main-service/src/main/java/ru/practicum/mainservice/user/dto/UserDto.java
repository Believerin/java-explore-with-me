package ru.practicum.mainservice.user.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDto {
    private int id;
    private String email;
    private String name;
}