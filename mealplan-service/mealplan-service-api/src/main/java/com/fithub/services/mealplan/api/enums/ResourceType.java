package com.fithub.services.mealplan.api.enums;

public enum ResourceType {

    MEAL_PLAN("MEAL_PLAN");

    private final String value;

    ResourceType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}