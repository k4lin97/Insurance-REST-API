package com.kalin.insurance.insurancerestapi.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Constraint that require a field to be valid with the values from a given directory.
 */
@Documented
@Constraint(validatedBy = MatchValuesFromDirectoryValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchValuesFromDirectoryConstraint {
    String fileDirectory();

    String message() default "Invalid ${validatedValue}.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
