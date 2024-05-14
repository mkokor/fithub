package com.fithub.services.auth.api.model.passwordresetcode;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordResetCodeRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "The user email must be provided.")
    private String userEmail;

}