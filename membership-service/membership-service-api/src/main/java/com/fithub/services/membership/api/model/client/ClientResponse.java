package com.fithub.services.membership.api.model.client;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a client response object.")
public class ClientResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String firstName;
    private String lastName;

}