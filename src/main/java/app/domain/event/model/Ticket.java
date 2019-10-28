package app.domain.event.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Embeddable
@Getter
@NoArgsConstructor
public class Ticket {

    @NotNull
    @Positive
    private Integer amount;
    private TicketType ticketType;

    public enum TicketType {
        FREE,
        PAID,
        DISCOUNT
    }
}
