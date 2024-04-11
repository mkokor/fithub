package com.fithub.services.membership.api.response;

public enum ApiErrorType {

    MODEL_VALIDATION("MODEL_VALIDATION"),

    BUSINESS_LOGIC("BUSINESS_LOGIC"),

    BAD_REQUEST("BED_REQUEST"),

    INTERNAL_SERVER("INTERNAL_SERVER");

    private final String value;

    ApiErrorType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}