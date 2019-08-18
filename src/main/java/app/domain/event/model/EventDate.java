package app.domain.event.model;

import lombok.Value;

import javax.persistence.Embeddable;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

@Embeddable
@Value
public class EventDate {

    @FutureOrPresent
    LocalDateTime startTime;

    @FutureOrPresent
    LocalDateTime endTime;

    private EventDate(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static EventDate createEventDate(LocalDateTime startTime, LocalDateTime endTime) {
        if (endTime.isBefore(startTime))
            throw new IllegalStateException("End date is before Start date");
        return new EventDate(startTime, endTime);
    }
}
