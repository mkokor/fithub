package com.fithub.services.mealplan.api.enums;

public enum ActionType {

    CREATE("CREATE"),

    GET("GET"),

    UPDATE("UPDATE"),

    DELETE("DELETE");

    private final String value;

    ActionType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}