package com.fithub.services.mealplan.api;

import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanUpdateRequest;

public interface DailyMealPlanService {

    DailyMealPlanResponse update(final DailyMealPlanUpdateRequest dailyMealPlanUpdateRequest) throws Exception;

}