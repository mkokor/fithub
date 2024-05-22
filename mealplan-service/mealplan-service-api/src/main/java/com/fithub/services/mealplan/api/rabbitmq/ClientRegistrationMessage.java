package com.fithub.services.mealplan.api.rabbitmq;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClientRegistrationMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String coachUuid;

}