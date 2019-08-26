package app.domain.event.model;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = {ValidEventDateValidator.class})
@Documented
public @interface CheckEventDate {

    String message() default "{app.domain.event.model." +
            "CheckEventDate.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
