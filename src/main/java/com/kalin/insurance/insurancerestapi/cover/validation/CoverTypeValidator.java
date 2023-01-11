package com.kalin.insurance.insurancerestapi.cover.validation;

import com.kalin.insurance.insurancerestapi.cover.Cover;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Checks whether the cover is valid - according to Cover.Type.isValid(..) method.
 */
public class CoverTypeValidator implements ConstraintValidator<CoverTypeConstraint, Cover.Type> {
    @Override
    public boolean isValid(Cover.Type type, ConstraintValidatorContext constraintValidatorContext) {
        return type != null && Cover.Type.isValid(type);
    }
}
