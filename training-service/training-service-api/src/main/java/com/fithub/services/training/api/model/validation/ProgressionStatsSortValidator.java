package com.fithub.services.training.api.model.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ProgressionStatsSortValidator implements ConstraintValidator<ProgressionStatsSort, String> {

    @Override
    public boolean isValid(String sortFilter, ConstraintValidatorContext context) {
        return sortFilter.equals("createdAt") || sortFilter.equals("benchPr") || sortFilter.equals("treadmillPr")
                || sortFilter.equals("deadliftPr") || sortFilter.equals("squatPr");
    }

}