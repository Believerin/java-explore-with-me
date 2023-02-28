package ru.practicum.mainservice.event.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class CheckDateValidator implements ConstraintValidator<StartInGivenHoursValid, LocalDateTime> {

    private int annotationHours;
    private boolean ifExists;

    @Override
    public void initialize(StartInGivenHoursValid constraintAnnotation) {
        this.annotationHours = constraintAnnotation.hours();
        this.ifExists = constraintAnnotation.ifExists();
    }

    @Override
    public boolean isValid(LocalDateTime start, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime requiredEventTime = LocalDateTime.now().plusHours(annotationHours);
        return ifExists ? (start == null || requiredEventTime.isBefore(start)) : requiredEventTime.isBefore(start);
    }
}