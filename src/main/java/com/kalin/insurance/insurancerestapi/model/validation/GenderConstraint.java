package com.kalin.insurance.insurancerestapi.model.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validation for a gender field of person entity.
 */
@Documented
@Constraint(validatedBy = GenderValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenderConstraint {
    String message() default "Gender ${validatedValue} is invalid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
