package app.domain.event.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//@Component
public class ValidEventDateValidator implements ConstraintValidator<CheckEventDate, EventDetails> {

    @Override
    public void initialize(CheckEventDate constraintAnnotation) {
    }

    @Override
    public boolean isValid(EventDetails eventDetails, ConstraintValidatorContext context) {
        if (eventDetails == null)
            return true;

        boolean isValid = eventDetails.getEventDate().getStartTime().isBefore(eventDetails.getEventDate().getEndTime());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("End date cannot be before start date")
                    .addPropertyNode("eventDate").addConstraintViolation();
        }

        return isValid;
    }
}
