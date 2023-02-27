package ru.practicum.mainservice.request.dto;

import lombok.*;
import ru.practicum.mainservice.request.status.RequestStatus;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private RequestStatus status;
}