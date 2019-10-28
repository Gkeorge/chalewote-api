package app.domain.event.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
public class EventDate {

    @NotNull
    @FutureOrPresent
    private LocalDateTime startTime;

    @NotNull
    @FutureOrPresent
    private LocalDateTime endTime;

}
