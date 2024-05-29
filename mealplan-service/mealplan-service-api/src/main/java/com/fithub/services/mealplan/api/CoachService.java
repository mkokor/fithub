package com.fithub.services.mealplan.api;

import java.util.List;

import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.client.ClientResponse;
import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.user.UserResponse;

public interface CoachService {
    
	 UserResponse getCoachNameAndLastName(String userId) throws NotFoundException;
    
	 List<ClientResponse> getClientsByCoach(Long coachId) throws Exception;
    
}
