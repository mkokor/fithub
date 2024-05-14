package com.fithub.services.auth.api.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

}