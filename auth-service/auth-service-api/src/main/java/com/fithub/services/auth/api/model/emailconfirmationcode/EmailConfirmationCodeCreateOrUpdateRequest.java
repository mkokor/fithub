package com.fithub.services.auth.api.model.emailconfirmationcode;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "The properties of an email confirmation code update object")
public class EmailConfirmationCodeCreateOrUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The email of the user")
    @NotBlank(message = "The email must not be blank.")
    @Email(message = "The email address is not valid.")
    private String userEmail;

}