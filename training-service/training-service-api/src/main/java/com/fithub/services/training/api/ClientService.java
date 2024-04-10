package com.fithub.services.training.api;

import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.model.client.CoachChangeRequest;
import com.fithub.services.training.api.model.client.CoachChangeResponse;

public interface ClientService {

    CoachChangeResponse changeCoach(CoachChangeRequest coachChangeRequest) throws ApiException;

}