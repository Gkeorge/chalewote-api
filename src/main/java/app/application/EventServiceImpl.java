package app.application;

import app.domain.event.model.Event;
import app.domain.event.model.EventDetails;
import app.domain.event.model.EventRepository;
import app.domain.user.model.User;
import app.domain.user.model.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Event getRegisteredEventForUser(UUID userId, UUID eventId) {
        return eventRepository.getEventForUser(userId, eventId);
    }

    @Override
    public Event registerEvent(UUID userId, EventDetails eventDetails) {
        User user = userRepository.loadUserByUserId(userId);
        return eventRepository.addEvent(user, eventDetails);
    }

    @Override
    public Event updateRegisteredEvent(UUID userId, UUID eventId, EventDetails eventDetails) {
        User user = userRepository.loadUserByUserId(userId);
        return eventRepository.updateRegisteredEvent(user, eventId, eventDetails);
    }

    @Override
    public void deleteRegisteredEvent(UUID eventId) {
        eventRepository.deleteEvent(eventId);
    }
}
