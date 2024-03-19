package com.fithub.services.auth.api;

import java.util.List;

import com.fithub.services.auth.api.model.coach.CoachResponse;

public interface CoachService {

    public List<CoachResponse> getAll();

}