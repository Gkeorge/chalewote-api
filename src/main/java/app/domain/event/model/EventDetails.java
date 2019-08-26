package app.domain.event.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.Valid;

@Embeddable
@Getter
public class EventDetails {

    //@NotBlank
    private String title;

    //@NotNull
    @Embedded
    private Location location;

    @Embedded
    @Valid
    private EventDate eventDate;

    private String image;

    private String description;

    @Embedded
    private Organizer organizer;

    private String eventType;

    private String eventTopic;

    @JsonCreator
    public EventDetails(@JsonProperty("title") String title, @JsonProperty("eventDate") EventDate eventDate) {
        this.title = title;
        this.description = description;
    }

    private EventDetails(EventDetailBuilder eventDetailBuilder) {
        this.title = eventDetailBuilder.title;
        this.location = eventDetailBuilder.location;
        this.eventDate = eventDetailBuilder.eventDate;
        this.image = eventDetailBuilder.image;
        this.description = eventDetailBuilder.description;
        this.organizer = eventDetailBuilder.organizer;
        this.eventType = eventDetailBuilder.eventType;
        this.eventTopic = eventDetailBuilder.eventTopic;
    }

    public static class EventDetailBuilder {

        String title;

        Location location;

        EventDate eventDate;

        String image;

        String description;

        Organizer organizer;

        String eventType;

        String eventTopic;

        public EventDetailBuilder(String title, Location location, EventDate eventDate, String description) {
            this.title = title;
            this.location = location;
            this.eventDate = eventDate;
            this.description = description;
        }

        public EventDetailBuilder withImage(String image) {
            this.image = image;
            return this;
        }

        public EventDetailBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public EventDetailBuilder withOrganizer(Organizer organizer) {
            this.organizer = organizer;
            return this;
        }

        public EventDetailBuilder withEventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public EventDetailBuilder withEventTopic(String eventTopic) {
            this.eventTopic = eventTopic;
            return this;
        }

        public EventDetails build() {
            return new EventDetails(this);
        }

    }
}
