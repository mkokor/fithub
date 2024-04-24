package com.fithub.services.mealplan.api.model.mealplan;
import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "The properties of meal plan request object")
public class NewMealPlanRequest implements Serializable{
	
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "The client id")
    @NotNull(message = "The client id must not be blank.")
    private Long clientId;
  
}
