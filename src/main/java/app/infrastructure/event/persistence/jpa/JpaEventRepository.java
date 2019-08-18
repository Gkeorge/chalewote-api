package app.infrastructure.event.persistence.jpa;

import app.domain.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface JpaEventRepository extends JpaRepository<Event, UUID> {

    @Query("Select e from Event e where e.user.userId = :userId and e.eventId = :eventId")
    Optional<Event> findByUserIdAndEventId(@Param("userId") UUID userId, @Param("eventId") UUID eventId);

    @Query("Select e from Event e where e.user.userId = :userId")
    Page<Event> findAllForUser(UUID userId, Pageable pageable);
}
