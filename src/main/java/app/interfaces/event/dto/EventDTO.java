package app.interfaces.event.dto;

import app.domain.event.model.Event;
import lombok.Value;

import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;
import java.util.UUID;

@Value
public class EventDTO {

    UUID userId;

    String title;

    String type;

    String venue;

    String address;

    String address2;

    String city;

    String zip;

    String country;

    String image;

    String description;

    @FutureOrPresent
    LocalDateTime startTime;

    @FutureOrPresent
    LocalDateTime endTime;

    String name;

    String OrganizerDescription;

    String eventType;

    String eventTopic;

    public static EventDTO toDTO(Event event) {
        return null;
    }

//    public static Event toEntity(UUID userId, EventDTO eventDTO) {
//        Location location = Location.builder().type(Location.LocationType.ONLINE).build();
//        EventDate date = EventDate.createEventDate(eventDTO.getStartTime(), eventDTO.getEndTime());
//        return new Event.EventBuilder(UserId.createUserID(userId), eventDTO.getTitle(), location, date).build();
//    }
}
