package com.fithub.services.auth.api.validation.validator;

import java.util.regex.Pattern;

import com.fithub.services.auth.api.validation.annotation.Password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        final String passwordStructureRegex = "^(?=.*[0-9])(?=.*[$#&%])[a-zA-Z0-9$#&%]{8,}$";
        return Pattern.matches(passwordStructureRegex, value);
    }

}