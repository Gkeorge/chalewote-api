package app.application;

import app.domain.event.model.Event;
import app.domain.event.model.EventDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventService {

    Page<Event> getRegisteredEvents(Pageable pageable);

    Event getRegisteredEvent(UUID eventId);

    Page<Event> getRegisteredEventsForUser(UUID userId, Pageable pageable);

    Event getRegisteredEventForUser(UUID userId, UUID eventId);

    Event registerEvent(UUID userId, EventDetails eventDetails);

    Event updateRegisteredEvent(UUID userId, UUID eventId, EventDetails eventDetails);

    void deleteRegisteredEvent(UUID eventId);
}
