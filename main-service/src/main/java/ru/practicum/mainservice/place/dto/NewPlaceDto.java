package ru.practicum.mainservice.place.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewPlaceDto {
    private BigDecimal lat;
    private BigDecimal lon;
    private float radius;
}