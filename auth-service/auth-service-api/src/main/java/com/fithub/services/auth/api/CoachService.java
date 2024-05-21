package com.fithub.services.auth.api;

import java.util.List;

import com.fithub.services.auth.api.coach.CoachLoV;
import com.fithub.services.auth.api.coach.CoachResponse;
import com.fithub.services.auth.api.exception.ApiException;

public interface CoachService {

    List<CoachLoV> getAvailableCoaches();

    CoachResponse getCoachById(Long id) throws ApiException;

}