package com.fithub.services.membership.api.enums;

public enum ResourceType {

    MEMBERSHIP("MEMBERSHIP");

    private final String value;

    ResourceType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}