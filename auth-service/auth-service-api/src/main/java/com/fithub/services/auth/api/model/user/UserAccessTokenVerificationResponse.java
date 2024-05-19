package com.fithub.services.auth.api.model.user;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserAccessTokenVerificationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userUuid;
    private String username;
    private String role;

}