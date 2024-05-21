package com.fithub.services.auth.api.coach;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoachLoV implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String displayName;

}