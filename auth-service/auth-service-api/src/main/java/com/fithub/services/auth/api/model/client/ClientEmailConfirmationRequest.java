package com.fithub.services.auth.api.model.client;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "The properties of an email confirmation request object")
public class ClientEmailConfirmationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The email of the client")
    private String email;

    @Schema(description = "The confirmation code")
    @NotBlank(message = "The confirmation code must not be blank.")
    private String confirmationCode;

}