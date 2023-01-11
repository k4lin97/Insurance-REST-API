package com.kalin.insurance.insurancerestapi.car.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * Validation for a car's production year - it should be between minimum production year and a current year.
 */
@Documented
@Constraint(validatedBy = CarProductionYearValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@NotNull
public @interface CarProductionYearConstraint {
    int minProductionYear() default 1980;

    String message() default "Invalid car production year. Value ${validatedValue} has to be between year {minProductionYear} and current year.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
