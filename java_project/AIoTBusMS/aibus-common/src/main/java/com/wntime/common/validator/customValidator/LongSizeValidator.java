package com.wntime.common.validator.customValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LongSizeValidator implements ConstraintValidator<LongSize, String> {

    private long min;
    private long max;

    @Override
    public void initialize(LongSize longSize) {
        min = longSize.min();
        max = longSize.max();
    }

    @Override
    public boolean isValid(String val, ConstraintValidatorContext constraintValidatorContext) {
        long n;
        try {
            n = Long.parseLong(val);
        } catch (Exception e) {
            return false;
        }
        return n >= min && n <= max;
    }

}
