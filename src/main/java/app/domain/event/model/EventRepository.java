package app.domain.event.model;

import app.domain.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface EventRepository {

    Page<Event> getEvents(Pageable pageable);

    Event addEvent(User user, EventDetails eventDetails);

    Event updateRegisteredEvent(User user, UUID eventID, EventDetails eventDetails);

    Event getEvent(UUID eventId);

    Event getEventForUser(UUID userId, UUID eventId);

    Page<Event> getRegisteredEventsForUser(UUID userId, Pageable pageable);

    void deleteEvent(UUID eventId);

}
