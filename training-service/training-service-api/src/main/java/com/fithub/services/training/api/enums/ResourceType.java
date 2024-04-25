package com.fithub.services.training.api.enums;

public enum ResourceType {

    CLIENT("CLIENT");

    private final String value;

    ResourceType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}