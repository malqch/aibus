package com.wntime.common.validator.customValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class DoubleSizeValidator implements ConstraintValidator<DoubleSize, String> {

    private double min;
    private double max;

    @Override
    public void initialize(DoubleSize doubleSize) {
        min = doubleSize.min();
        max = doubleSize.max();
    }

    @Override
    public boolean isValid(String val, ConstraintValidatorContext constraintValidatorContext) {
        double n;
        try {
            n = Double.parseDouble(val);
        } catch (Exception e) {
            return false;
        }
        return n >= min && n <= max;
    }

}
