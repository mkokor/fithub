package com.fithub.services.mealplan.api;

import java.util.List;

import com.fithub.services.mealplan.api.model.coach.CoachResponse;

public interface CoachService {

    public List<CoachResponse> getAll();

}