package com.fithub.services.training.api.response;

public enum ApiErrorType {

    MODEL_VALIDATION("MODEL_VALIDATION"),

    BUSINESS_LOGIC("BUSINESS_LOGIC"),

    INTERNAL_SERVER("INTERNAL_SERVER"),

    BAD_REQUEST("BAD_REQUEST");

    private final String value;

    ApiErrorType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}