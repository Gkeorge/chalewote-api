package app.domain.event.model;


import app.domain.user.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Data
public class Event {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID eventId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @Embedded
    private EventDetails eventDetails;

    private Event(User user, EventDetails eventDetails) {
        this.user = user;
        this.eventDetails = eventDetails;
    }

    private Event(User user, UUID eventId, EventDetails eventDetails) {
        this.user = user;
        this.eventId = eventId;
        this.eventDetails = eventDetails;
    }

    public static Event createEvent(User user, EventDetails eventDetails) {
        return new Event(user, eventDetails);
    }

    public static Event createEvent(User user, UUID eventId, EventDetails eventDetails) {
        return new Event(user, eventId, eventDetails);
    }

}
