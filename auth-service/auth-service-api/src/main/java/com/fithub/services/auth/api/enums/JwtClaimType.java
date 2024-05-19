package com.fithub.services.auth.api.enums;

public enum JwtClaimType {

    USERNAME("username"), ROLE("role");

    private String value;

    private JwtClaimType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}