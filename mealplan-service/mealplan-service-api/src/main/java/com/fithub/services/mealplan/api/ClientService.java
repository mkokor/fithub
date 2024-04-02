package com.fithub.services.mealplan.api;

import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.api.model.user.UserResponse;

public interface ClientService {
	
	MealPlanResponse getMealPlan(Long clientId) throws Exception;
	
	UserResponse getClientNameAndLastName(String userId) throws NotFoundException;
}
