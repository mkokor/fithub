package com.fithub.services.auth.api.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.fithub.services.auth.api.validation.validator.PasswordValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {

    public String message() default "The password must contain at least 8 characters, including one digit and one special character.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}