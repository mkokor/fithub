package com.fithub.services.chat.api.enums;

public enum ResourceType {

    CHAT_MESSAGE("CHAT_MESSAGE");

    private final String value;

    ResourceType(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
	
}