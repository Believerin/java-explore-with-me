package ru.practicum.mainservice.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.mainservice.request.model.ParticipationRequest;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface RequestUserRepository extends JpaRepository<ParticipationRequest, Integer> {

    @Query("select r " +
           "from ParticipationRequest r " +
           "left join Event e on r.event.id = e.id " +
           "where e.initiator.id = ?1 and r.event.id = ?2")
    Collection<ParticipationRequest> findByOwnerAndEventsId(int userId, int eventId);

    Collection<ParticipationRequest> findByRequesterId(int userId);

    Optional<ParticipationRequest> findByIdAndRequesterId(int requestId, int userId);

    @Query("select r " +
           "from ParticipationRequest r " +
           "left join Event e on r.event.id = e.id " +
           "where e.initiator.id = ?1 and r.event.id = ?2 and r.id in ?3")
    Collection<ParticipationRequest> findByInitiatorAndEventIdAndRequestsId(int userId, int eventId, List<Integer> requestIds);
}