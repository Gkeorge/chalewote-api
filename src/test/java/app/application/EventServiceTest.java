package app.application;

import app.domain.event.model.Event;
import app.domain.event.model.EventDetails;
import app.domain.event.model.EventRepository;
import app.domain.event.model.Location;
import app.domain.user.model.User;
import app.domain.user.model.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    private EventService eventService;

    @BeforeEach
    public void setup() {
        eventService = new EventServiceImpl(eventRepository, userRepository);
    }

    @Test
    void getRegisteredEvents() {
    }

    @Test
    void getRegisteredEvent() {
        UUID eventId = UUID.randomUUID();
        Event event = Event.createEvent(new User(), eventId,
                new EventDetails.EventDetailBuilder("CIAS", new Location(Location.LocationType.ONLINE, null),
                        null, "Test").build());
        given(eventRepository.getEvent(eventId)).willReturn(event);

        Event registeredEvent = eventService.getRegisteredEvent(eventId);
        assertAll("event",
                () -> assertThat(registeredEvent.getEventDetails().getDescription()).isEqualTo("Test"),
                () -> assertThat(registeredEvent.getEventDetails().getTitle()).isEqualTo("CIAS"));

        Mockito.verify(eventRepository, times(1))
                .getEvent(eventId);
        verifyNoMoreInteractions(eventRepository);
    }

    @Test
    void getRegisteredEventsForUser() {
    }

    @Test
    void getRegisteredEventForUser() {
    }

    @Test
    void registerEvent() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        Event event = Event.createEvent(user,
                new EventDetails.EventDetailBuilder("CIAS", new Location(Location.LocationType.ONLINE, null),
                        null, "Test").build());
        given(userRepository.loadUserByUserId(userId)).willReturn(user);
        given(eventRepository.addEvent(user, event.getEventDetails())).willReturn(event);

        Event registeredEvent = eventService.registerEvent(userId, event.getEventDetails());
        assertAll("",
                () -> assertThat(registeredEvent.getEventDetails().getTitle()).isEqualTo("CIAS"),
                () -> assertThat(registeredEvent.getEventDetails().getDescription()).isEqualTo("Test"));
    }

    @Test
    void updateRegisteredEvent() {

        UUID userId = UUID.randomUUID();
        UUID eventId = UUID.randomUUID();
        User user = new User();
        Event event = Event.createEvent(user,
                new EventDetails.EventDetailBuilder("CIAS", new Location(Location.LocationType.ONLINE, null),
                        null, "Test").build());
        given(userRepository.loadUserByUserId(userId)).willReturn(user);
        given(eventRepository.updateRegisteredEvent(user, eventId, event.getEventDetails())).willReturn(event);

        Event registeredEvent = eventService.updateRegisteredEvent(userId, eventId, event.getEventDetails());
        assertAll("",
                () -> assertThat(registeredEvent.getEventDetails().getTitle()).isEqualTo("CIAS"),
                () -> assertThat(registeredEvent.getEventDetails().getDescription()).isEqualTo("Test"));
    }

    @Test
    void deleteRegisteredEvent() {
    }
}