package com.fithub.services.mealplan.api.model.dailymealplan;

import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of daily meal plan response object")
public class DailyMealPlanResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//private Long id;
	
	private String day;
	
	private String breakfast;
	
	private String amSnack;
	
	private String lunch;
	
	private String dinner;
	
	private String pmSnack;
	
	//private Long mealPlanId;

	
}
