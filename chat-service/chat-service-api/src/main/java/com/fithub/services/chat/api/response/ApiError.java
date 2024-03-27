package com.fithub.services.chat.api.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errorType;

    private String message;

    public ApiError(ApiErrorType errorType, String message) {
        this.errorType = errorType.getValue();
        this.message = message;
    }

}