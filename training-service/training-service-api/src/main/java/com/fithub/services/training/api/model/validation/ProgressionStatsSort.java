package com.fithub.services.training.api.model.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ProgressionStatsSortValidator.class)
public @interface ProgressionStatsSort {

    public String message() default "The provided string is not a valid progression stats sort filter.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}