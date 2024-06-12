package com.fithub.services.training.api.model.coach;

import java.io.Serializable;

import lombok.Data;

@Data
public class CoachResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String firstName;
    private String lastName;

}