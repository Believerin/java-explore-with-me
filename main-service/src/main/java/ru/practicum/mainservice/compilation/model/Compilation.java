package ru.practicum.mainservice.compilation.model;

import lombok.*;
import ru.practicum.mainservice.event.model.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "compilations", schema = "public")
public class Compilation {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "compilation_id")
    private List<Event> events;
    private boolean pinned;
    private String title;
}