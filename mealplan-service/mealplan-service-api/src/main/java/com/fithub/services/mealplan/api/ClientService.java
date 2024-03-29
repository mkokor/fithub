package com.fithub.services.mealplan.api;

import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;

public interface ClientService {
	
	MealPlanResponse getMealPlan(Long clientId) throws Exception;
}
