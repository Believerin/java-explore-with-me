package ru.practicum.statsserviceserver.repository;

import org.springframework.data.jpa.repository.*;
import ru.practicum.statsserviceserver.model.*;

import java.time.LocalDateTime;

public interface EndPointHitRepository extends JpaRepository<EndPointHit, Integer> {

    @Query(value = "select new ru.practicum.statsserviceserver.model.ViewStats(e.app, e.uri, count(e)) " +
            "from EndPointHit e " +
            "where e.timestamp >= ?1 and e.timestamp <= ?2 and e.uri in ?3 " +
            "group by e.app, e.uri")
    ViewStats findAllByParameters(LocalDateTime start, LocalDateTime end, String[] uris, Class<ViewStats> type);

    @Query(value = "select new ru.practicum.statsserviceserver.model.ViewStats(e.app, e.uri, count(e)) " +
            "from EndPointHit e " +
            "where e.timestamp >= ?1 and e.timestamp <= ?2 and e.uri in ?3 " +
            "group by e.app, e.uri, e.ip")
    ViewStats findAllUniqueIpByParameters(LocalDateTime start, LocalDateTime end, String[] uris, Class<ViewStats> type);
}