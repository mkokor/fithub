package com.fithub.services.mealplan.api.model.user;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of user response object")
public class UserResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String firstName;
	
    private String lastName;
	
}
