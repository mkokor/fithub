package com.fithub.services.auth.api.model.user;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a user sign in response object")
public class UserSignInResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String accessToken;
    private String role;
    private String username;

}