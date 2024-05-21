package com.fithub.services.mealplan.api;

import java.util.List;

import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.MealPlanUpdateRequest;

public interface MealPlanService {
	List<DailyMealPlanResponse> getDailyMealByDay(Long mealPlanId) throws Exception;

	List<DailyMealPlanResponse> updateMealPlan(Long clientId, MealPlanUpdateRequest mealPlanUpdateRequest)
			throws Exception;

}


