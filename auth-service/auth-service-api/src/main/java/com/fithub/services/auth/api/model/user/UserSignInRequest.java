package com.fithub.services.auth.api.model.user;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "The properties of a user sign in request object")
public class UserSignInRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The username of the user")
    @NotBlank(message = "The username must be specified.")
    private String username;

    @Schema(description = "The password of the user")
    @NotBlank(message = "The password must be specified.")
    private String password;

}