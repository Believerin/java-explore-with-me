package ru.practicum.mainservice.event.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.mainservice.category.model.Category;
import ru.practicum.mainservice.event.status.State;
import ru.practicum.mainservice.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "events", schema = "public")
public class Event {
    @EqualsAndHashCode.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String annotation;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;
    private int confirmedRequests;
    @CreationTimestamp
    private LocalDateTime createdOn;
    private String description;
    private LocalDateTime eventDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "initiator_id")
    private User initiator;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumns({@JoinColumn(name = "lat"), @JoinColumn(name = "lon")})
    private Location location;
    private boolean paid;
    private int participantLimit;
    private LocalDateTime publishedOn;
    private boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state;
    private String title;
    private int views;
}