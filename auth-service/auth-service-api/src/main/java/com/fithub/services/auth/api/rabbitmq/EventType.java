package com.fithub.services.auth.api.rabbitmq;

import lombok.Getter;

@Getter
public enum EventType {

    PENDING("Pending"), ERROR("Error"), SUCCESS("Success");

    private String value;

    private EventType(String value) {
        this.value = value;
    }

}