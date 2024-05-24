package com.fithub.services.training.api.rabbitmq;

import lombok.Getter;

@Getter
public enum EventType {

    PENDING("Pending"), ERROR("Error"), SUCCESS("Success");

    private String value;

    private EventType(String value) {
        this.value = value;
    }

}