package com.fithub.services.mealplan.api;

import com.fithub.services.mealplan.api.exception.ApiException;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.dao.model.ClientEntity;

public interface MealPlanService {

    MealPlanResponse createMealPlan(final ClientEntity clientEntity);

    MealPlanResponse getMealPlanByClientUuid(final String clientUuid) throws ApiException;

    MealPlanResponse getMealPlanClient();

}