package com.fithub.services.auth.api.coach;

import java.io.Serializable;

import lombok.Data;

@Data
public class CoachResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
    private String lastName;
    private String biography;
    private String imagePath;

}