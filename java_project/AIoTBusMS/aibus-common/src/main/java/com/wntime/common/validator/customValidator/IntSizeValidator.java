package com.wntime.common.validator.customValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IntSizeValidator implements ConstraintValidator<IntSize, String> {

    private int min;
    private int max;

    @Override
    public void initialize(IntSize intSize) {
        min = intSize.min();
        max = intSize.max();
    }

    @Override
    public boolean isValid(String val, ConstraintValidatorContext constraintValidatorContext) {
        if (val == null || val.equals("")) return true;
        int n;
        try {
            n = Integer.parseInt(val);
        } catch (Exception e) {
            return false;
        }
        return n >= min && n <= max;
    }

}
