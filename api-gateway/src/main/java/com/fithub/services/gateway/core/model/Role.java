package com.fithub.services.gateway.core.model;

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