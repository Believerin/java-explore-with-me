package ru.practicum.mainservice.event.model;

import lombok.*;

import javax.persistence.*;

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
    private float lat;
    @Id
    private float lon;
}