package app.domain.event.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EventRepository {

    Page<Event> getEvents(Pageable pageable);

    Event addEvent(Event event);

    Event updateRegisteredEvent(Event event);

    Event getEvent(UUID eventId);

    Page<Event> getRegisteredEventsForUser(UUID userId, Pageable pageable);

    void deleteEvent(UUID eventId);

}
