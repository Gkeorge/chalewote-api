package app.infrastructure.event.persistence.jpa;

import app.domain.event.model.Event;
import app.domain.event.model.EventDetails;
import app.domain.event.model.EventRepository;
import app.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Transactional
@Repository
@RequiredArgsConstructor
class DefaultEventRepository implements EventRepository {

    private final JpaEventRepository jpaEventRepository;

    @Override
    public Page<Event> getEvents(Pageable pageable) {
        return jpaEventRepository.findAll(pageable);
    }

    @Override
    public Event addEvent(User user, EventDetails eventDetails) {
        return jpaEventRepository.save(Event.createEvent(user, eventDetails));
    }

    @Override
    public Event updateRegisteredEvent(User user, UUID eventID, EventDetails eventDetails) {
        return jpaEventRepository.save(Event.createEvent(user, eventID, eventDetails));
    }

    @Override
    public Event getEvent(UUID eventId) {
        return jpaEventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

    @Override
    public Event getEventForUser(UUID userId, UUID eventId) {
        return jpaEventRepository.findByUserIdAndEventId(userId, eventId)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

    @Override
    public Page<Event> getRegisteredEventsForUser(UUID userId, Pageable pageable) {
        return jpaEventRepository.findAllForUser(userId, pageable);
    }

    @Override
    public void deleteEvent(UUID eventId) {
        boolean present = jpaEventRepository.findById(eventId)
                .isPresent();
        if (present)
            jpaEventRepository.deleteById(eventId);
        else
            throw new EntityNotFoundException("");
    }
}
