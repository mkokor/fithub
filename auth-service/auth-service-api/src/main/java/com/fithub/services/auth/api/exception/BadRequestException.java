package com.fithub.services.auth.api.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}