package com.fithub.services.mealplan.api.model.mealplan;

import java.io.Serializable;
import java.time.LocalDateTime;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of meal plan response object")
public class MealPlanResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long clientId;
	
    private LocalDateTime modified;
    
    private Long modifiedBy;

	
}
