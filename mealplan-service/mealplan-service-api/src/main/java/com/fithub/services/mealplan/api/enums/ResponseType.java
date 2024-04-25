package com.fithub.services.mealplan.api.enums;

public enum ResponseType {

    SUCCESS("SUCCESS"),

    ERROR("ERROR");

    private final String value;

    ResponseType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}