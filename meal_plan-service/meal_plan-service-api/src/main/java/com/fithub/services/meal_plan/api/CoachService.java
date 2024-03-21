package com.fithub.services.meal_plan.api;

import java.util.List;

import com.fithub.services.meal_plan.api.model.coach.CoachResponse;

public interface CoachService {

    public List<CoachResponse> getAll();

}