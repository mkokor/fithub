package com.fithub.services.gateway.core.model;

public enum ApiErrorType {

    AUTHORIZATION("AUTHORIZATION"), INTERNAL_SERVER("INTERNAL_SERVER");

    private final String value;

    ApiErrorType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}