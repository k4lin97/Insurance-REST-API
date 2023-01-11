package com.kalin.insurance.insurancerestapi.car.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Year;

/**
 * Validator for car's production year. Checks if the year is between specified year, and current year.
 */
public class CarProductionYearValidator implements ConstraintValidator<CarProductionYearConstraint, Integer> {

    private final Integer currentYear = Year.now().getValue();
    private Integer minProductionYear;

    @Override
    public void initialize(CarProductionYearConstraint constraintAnnotation) {
        minProductionYear = constraintAnnotation.minProductionYear();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer productionYear, ConstraintValidatorContext constraintValidatorContext) {
        return (productionYear >= minProductionYear) && (productionYear <= currentYear);
    }
}
