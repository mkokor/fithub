package com.fithub.services.membership.core.validation.validator;

import java.time.Month;

import com.fithub.services.membership.core.validation.annotation.MonthOfYear;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MonthOfYearValidator implements ConstraintValidator<MonthOfYear, String> {

    @Override
    public boolean isValid(String monthTitle, ConstraintValidatorContext context) {
        try {
            Month.valueOf(monthTitle);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

}