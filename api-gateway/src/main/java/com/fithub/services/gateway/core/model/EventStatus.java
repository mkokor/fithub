package com.fithub.services.gateway.core.model;

public enum EventStatus {

    SUCCESS("SUCCESS"),

    ERROR("ERROR");

    private final String value;

    EventStatus(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}