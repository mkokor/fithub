package com.fithub.services.mealplan.api;

import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.api.model.user.UserResponse;

public interface CoachService {
    
	 UserResponse getCoachNameAndLastName(String userId) throws NotFoundException;
    
    
}
