package com.fithub.services.membership.api.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}