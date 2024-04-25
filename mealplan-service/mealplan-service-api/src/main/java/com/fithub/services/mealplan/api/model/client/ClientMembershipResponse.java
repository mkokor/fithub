package com.fithub.services.mealplan.api.model.client;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The personal data about the membership owner.")
public class ClientMembershipResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String firstName;

    private String lastName;

    private String email;

}