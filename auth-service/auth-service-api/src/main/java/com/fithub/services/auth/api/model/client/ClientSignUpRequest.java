package com.fithub.services.auth.api.model.client;

import java.io.Serializable;

import com.fithub.services.auth.api.validation.annotation.Password;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "The properties of a client sign up request object")
public class ClientSignUpRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The first name of the client")
    @NotBlank(message = "The first name must not be blank.")
    private String firstName;

    @Schema(description = "The last name of the client")
    @NotBlank(message = "The last name must not be blank.")
    private String lastName;

    @Schema(description = "The username of the client")
    @NotBlank(message = "The username must not be blank.")
    @Size(min = 3, message = "The username must be at least 3 characters long.")
    private String username;

    @Schema(description = "The email of the client")
    @NotBlank(message = "The email must not be blank.")
    @Email(message = "The email address is not valid.")
    private String email;

    @Schema(description = "The password to be set on client's account")
    @Password
    private String password;

    @Schema(description = "The ID of a coach to be set on a client")
    @NotNull(message = "The coach ID must not be null.")
    private Long coachId;

}