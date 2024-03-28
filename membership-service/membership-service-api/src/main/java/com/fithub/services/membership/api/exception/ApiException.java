package com.fithub.services.membership.api.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends Exception {

    private static final long serialVersionUID = 1L;

    private HttpStatus statusCode;

    public ApiException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

}
