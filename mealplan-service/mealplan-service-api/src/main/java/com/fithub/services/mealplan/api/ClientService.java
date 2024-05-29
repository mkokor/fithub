package com.fithub.services.mealplan.api;

import java.util.List;

import com.fithub.services.mealplan.api.exception.ApiException;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.client.ClientResponse;
import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.api.model.user.NewUserRequest;
import com.fithub.services.mealplan.api.model.user.UserResponse;
import com.fithub.services.mealplan.api.rabbitmq.ClientRegistrationMessage;

public interface ClientService {

    MealPlanResponse getMealPlan(Long clientId) throws Exception;

    UserResponse getClientNameAndLastName(String userId) throws NotFoundException;

    List<DailyMealPlanResponse> getDailyMealPlanByClientId(Long clientId) throws Exception;
    
    //ClientResponse getClientResponse(String userId) throws Exception;

    MealPlanResponse makeMealPlanForClient(String userId) throws Exception;

    CoachResponse postCoachForClient(String userId, NewUserRequest newUserRequest) throws Exception;

    void addClient(final ClientRegistrationMessage clientRegistrationMessage) throws ApiException;

}