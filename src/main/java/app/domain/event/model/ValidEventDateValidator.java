package app.domain.event.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidEventDateValidator implements ConstraintValidator<CheckEventDate, EventDetails> {

    @Override
    public void initialize(CheckEventDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(EventDetails eventDetails, ConstraintValidatorContext context) {
        if (eventDetails == null)
            return true;

        if (eventDetails.getEventDate() == null)
            return true;

        boolean isValid = false;

        if (eventDetails.getEventDate().getStartTime() != null && eventDetails.getEventDate().getEndTime() != null)
            isValid = eventDetails.getEventDate().getStartTime().isBefore(eventDetails.getEventDate().getEndTime());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Valid event dates are required. " +
                            "Please check docs for info on valid dates")
                    .addPropertyNode("eventDate").addConstraintViolation();
        }

        return isValid;
    }
}
