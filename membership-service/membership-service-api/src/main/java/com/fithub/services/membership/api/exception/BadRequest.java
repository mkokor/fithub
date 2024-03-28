package com.fithub.services.membership.api.exception;

import org.springframework.http.HttpStatus;

public class BadRequest extends ApiException {

    private static final long serialVersionUID = 1L;

    public BadRequest(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}
