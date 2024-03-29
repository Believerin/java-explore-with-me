package ru.practicum.mainservice.event.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationId implements Serializable {
    private BigDecimal lat;
    private BigDecimal lon;
}
