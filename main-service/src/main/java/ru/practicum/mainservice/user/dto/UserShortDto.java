package ru.practicum.mainservice.user.dto;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserShortDto {
    private int id;
    private String name;
}
