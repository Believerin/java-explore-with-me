package ru.practicum.mainservice.request;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.practicum.mainservice.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.mainservice.request.dto.ParticipationRequestDto;
import ru.practicum.mainservice.request.model.ParticipationRequest;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    @Mapping(source = "destination.event.id", target = "event")
    @Mapping(source = "destination.requester.id", target = "requester")
    @Mapping(source = "created", target = "created",  dateFormat = "yyyy-MM-dd' 'HH:mm:ss")
    ParticipationRequestDto toParticipationRequestDto(ParticipationRequest destination);

    @Mapping(source = "destination", target = "rejectedRequests")
    EventRequestStatusUpdateResult toRejectedResult(int count, List<ParticipationRequestDto> destination);

    @Mapping(source = "destination", target = "confirmedRequests")
    EventRequestStatusUpdateResult toConfirmedResult(int count, List<ParticipationRequestDto> destination);
}