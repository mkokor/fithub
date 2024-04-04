package com.fithub.services.mealplan.api;

import java.util.List;

import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.api.model.user.UserResponse;

public interface ClientService {
	
	MealPlanResponse getMealPlan(Long clientId) throws Exception;
	
	UserResponse getClientNameAndLastName(String userId) throws NotFoundException;
	
	List<DailyMealPlanResponse> getDailyMealPlanByClientId(Long clientId) throws Exception;

}
