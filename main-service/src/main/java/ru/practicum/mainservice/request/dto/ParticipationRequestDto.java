package ru.practicum.mainservice.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import ru.practicum.mainservice.request.status.RequestStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ParticipationRequestDto {
    private int id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd' 'HH:mm:ss")
    private LocalDateTime created;
    private int event;
    private int requester;
    private RequestStatus status;
}