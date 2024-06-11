package com.fithub.services.training.api.model.client;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClientResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String firstName;
    private String lastName;

}