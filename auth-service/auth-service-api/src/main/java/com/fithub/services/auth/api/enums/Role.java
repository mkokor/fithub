package com.fithub.services.auth.api.enums;

public enum Role {

    CLIENT("CLIENT"), COACH("COACH");

    private String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}