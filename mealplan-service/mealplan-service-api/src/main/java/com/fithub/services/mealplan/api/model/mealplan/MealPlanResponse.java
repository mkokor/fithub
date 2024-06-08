package com.fithub.services.mealplan.api.model.mealplan;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fithub.services.mealplan.api.model.client.ClientResponse;
import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a meal plan response object.")
public class MealPlanResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private ClientResponse client;
    private LocalDateTime lastModified;
    private CoachResponse lastModifiedBy;
    private List<DailyMealPlanResponse> dailyMealPlans;

}