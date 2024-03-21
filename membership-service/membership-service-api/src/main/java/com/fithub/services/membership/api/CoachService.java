package com.fithub.services.membership.api;

import java.util.List;

import com.fithub.services.membership.api.model.coach.CoachResponse;

public interface CoachService {

    public List<CoachResponse> getAll();

}