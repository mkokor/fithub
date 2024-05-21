package com.fithub.services.mealplan.api.model.dailymealplan;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "The properties of a meal plan update response object")
public class MealPlanUpdateRequest {

	
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String day;
    
    
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String breakfast;
    
  
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String amSnack;
    
 
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String lunch;
    

    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String dinner;
    
   
    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String pmSnack;
    
    
    //private Long coachId;
    
   
}
