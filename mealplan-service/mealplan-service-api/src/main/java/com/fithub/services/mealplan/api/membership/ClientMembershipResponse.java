package com.fithub.services.mealplan.api.membership;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClientMembershipResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    private String firstName;

    private String lastName;

    private String email;

}