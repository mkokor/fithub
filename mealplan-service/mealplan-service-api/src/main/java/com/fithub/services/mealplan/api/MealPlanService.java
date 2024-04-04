package com.fithub.services.mealplan.api;

import java.util.List;

import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;

public interface MealPlanService {
	List<DailyMealPlanResponse> getDailyMealByDay(Long mealPlanId) throws Exception;

}
