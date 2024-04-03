package com.fithub.services.training.api.exception;

import org.springframework.http.HttpStatus;

public class ThirdPartyApiException extends Exception {

    private static final long serialVersionUID = 1L;

    private HttpStatus statusCode;

    public ThirdPartyApiException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

}