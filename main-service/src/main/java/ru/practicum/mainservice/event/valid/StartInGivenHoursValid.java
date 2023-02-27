package ru.practicum.mainservice.event.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = CheckDateValidator.class)
public @interface StartInGivenHoursValid {
    String message() default "Времени до начала недостаточно";

    boolean ifExists() default true;

    int hours();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}