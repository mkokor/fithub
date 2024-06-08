package com.fithub.services.mealplan.api.model.coach;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a coach response object.")
public class CoachResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private String firstName;
    private String lastName;

}