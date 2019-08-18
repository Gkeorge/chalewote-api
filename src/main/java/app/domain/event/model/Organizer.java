package app.domain.event.model;

import lombok.Value;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Value
class Organizer {

    String name;

    @Column(name = "organizer_description")
    String description;
}
