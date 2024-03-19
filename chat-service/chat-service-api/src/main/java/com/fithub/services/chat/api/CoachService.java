package com.fithub.services.chat.api;

import java.util.List;

import com.fithub.services.chat.api.model.coach.CoachResponse;

public interface CoachService {

    public List<CoachResponse> getAll();

}