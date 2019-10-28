package app.application;

import app.domain.event.model.Event;
import app.domain.event.model.EventDetails;
import app.domain.event.model.EventRepository;
import app.domain.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Event> getRegisteredEvents(Pageable pageable) {
        return eventRepository.getEvents(pageable);
    }

    @Override
    public Event getRegisteredEvent(UUID eventId) {
        return eventRepository.getEvent(eventId);
    }

    @Override
    public Page<Event> getRegisteredEventsForUser(UUID userId, Pageable pageable) {
        return eventRepository.getRegisteredEventsForUser(userId, pageable);
    }

    @Override
    public Event registerEvent(UUID userId, EventDetails eventDetails) {
        return eventRepository.addEvent(Event.createEvent(userRepository.loadUserByUserId(userId), eventDetails));
    }

    @Override
    public Event updateRegisteredEvent(UUID userId, UUID eventId, EventDetails eventDetails) {
        return eventRepository.updateRegisteredEvent(Event.createEvent(userRepository.loadUserByUserId(userId), eventId, eventDetails));
    }

    @Override
    public void deleteRegisteredEvent(UUID eventId) {
        Event event = eventRepository.getEvent(eventId);
        if (event != null)
            eventRepository.deleteEvent(eventId);
        else
            throw new EntityNotFoundException("Event not found for ID: " + eventId);
    }
}
