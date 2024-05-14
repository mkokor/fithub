package com.fithub.services.auth.api.enums;

public enum AccessTokenClaimType {

    EMAIL("email"), USERNAME("username"), ROLE("role");

    private String value;

    private AccessTokenClaimType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}