package ru.practicum.mainservice.event.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "locations", schema = "public")
@IdClass(LocationId.class)
public class Location {
    @EqualsAndHashCode.Exclude
    @Id
    private BigDecimal lat;
    @Id
    private BigDecimal lon;
}