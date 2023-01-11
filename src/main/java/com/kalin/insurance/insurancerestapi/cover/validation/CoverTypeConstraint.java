package com.kalin.insurance.insurancerestapi.cover.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validation for a cover type.
 */
@Documented
@Constraint(validatedBy = CoverTypeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CoverTypeConstraint {
    String message() default "Cover type: ${validatedValue} is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
