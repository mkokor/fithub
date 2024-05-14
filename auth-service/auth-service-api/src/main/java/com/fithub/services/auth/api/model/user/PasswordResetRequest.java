package com.fithub.services.auth.api.model.user;

import java.io.Serializable;

import com.fithub.services.auth.api.validation.annotation.Password;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "The properties of a password reset request object")
public class PasswordResetRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The email of the user")
    private String userEmail;

    @Schema(description = "The password reset code")
    @NotBlank(message = "The password reset code must be specified.")
    private String passwordResetCode;

    @Schema(description = "The new password of the user")
    @Password
    private String newPassword;

}