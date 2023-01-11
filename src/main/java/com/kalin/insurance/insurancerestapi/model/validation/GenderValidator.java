package com.kalin.insurance.insurancerestapi.model.validation;

import com.kalin.insurance.insurancerestapi.model.PersonEntity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validates whether the gender is not null an is valid meaning PersonEntity.Gender.isValid(..) outputs true.
 */
public class GenderValidator implements ConstraintValidator<GenderConstraint, PersonEntity.Gender> {
    @Override
    public boolean isValid(PersonEntity.Gender gender, ConstraintValidatorContext constraintValidatorContext) {
        return gender != null && PersonEntity.Gender.isValid(gender);
    }
}
