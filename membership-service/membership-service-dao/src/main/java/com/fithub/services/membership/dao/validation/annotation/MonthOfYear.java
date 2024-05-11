package com.fithub.services.membership.dao.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fithub.services.membership.dao.validation.validator.MonthOfYearValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = MonthOfYearValidator.class)
public @interface MonthOfYear {

    public String message() default "The provided string is not a valid month title.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}