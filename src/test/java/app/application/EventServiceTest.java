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
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
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
    void setup() {
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
        User user = getUser();

        ArgumentCaptor<UUID> uuidArgumentCaptor = ArgumentCaptor.forClass(UUID.class);

        Event event = getEvent(user);

        given(userRepository.loadUserByUserId(user.getUserId())).willReturn(user);
        given(eventRepository.addEvent(event)).willReturn(event);

        Event registeredEvent = eventService.registerEvent(user.getUserId(), event.getEventDetails());

        then(userRepository).should().loadUserByUserId(uuidArgumentCaptor.capture());
        then(eventRepository).should().addEvent(event);
        then(eventRepository).shouldHaveNoMoreInteractions();
        then(userRepository).shouldHaveNoMoreInteractions();

        assertAll("",
                () -> assertThat(registeredEvent.getEventDetails().getTitle()).isEqualTo("CIAS"),
                () -> assertThat(uuidArgumentCaptor.getValue().equals(user.getUserId())),
                () -> assertThat(registeredEvent.getEventDetails().getDescription()).isEqualTo("Test"));
    }

    private Event getEvent(User user) {
        return Event.createEvent(user,
                    new EventDetails.EventDetailBuilder("CIAS", new Location(Location.LocationType.ONLINE, null),
                            null, "Test").build());
    }

    private User getUser() {
        return User.builder().emailAddress("gorkofi@gmail.com")
                .password("123456").userId(UUID.randomUUID()).build();
    }

    @Test
    void updateRegisteredEvent() {

        UUID eventId = UUID.randomUUID();
        User user = getUser();

        Event event = Event.createEvent(user, eventId,
                new EventDetails.EventDetailBuilder("CIAS", new Location(Location.LocationType.ONLINE, null),
                        null, "Test").build());

        given(userRepository.loadUserByUserId(user.getUserId())).willReturn(user);
        given(eventRepository.updateRegisteredEvent(event)).willReturn(event);

        Event registeredEvent = eventService.updateRegisteredEvent(user.getUserId(), eventId, event.getEventDetails());

        assertAll("",
                () -> assertThat(registeredEvent.getEventDetails().getTitle()).isEqualTo("CIAS"),
                () -> assertThat(registeredEvent.getEventDetails().getDescription()).isEqualTo("Test"));
    }

    @Test
    void deleteRegisteredEvent() {
    }

}

//        Mockito.verify(userRepository)
//                .loadUserByUserId(userId);
//        verifyNoMoreInteractions(userRepository);
//        verifyNoMoreInteractions(eventRepository);