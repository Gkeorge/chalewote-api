package app.domain.event.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
public class EventDate {

    @FutureOrPresent
    private LocalDateTime startTime;

    @FutureOrPresent
    private LocalDateTime endTime;

    @JsonCreator
    public EventDate(@JsonProperty("startTime") LocalDateTime startTime, @JsonProperty("endTime") LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

}
