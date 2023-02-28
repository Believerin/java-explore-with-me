package ru.practicum.mainservice.category.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "categories", schema = "public")
public class Category {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}