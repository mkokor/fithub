package com.fithub.services.auth.api;

import java.util.List;

import com.fithub.services.auth.api.coach.CoachLoV;
import com.fithub.services.auth.api.coach.CoachResponse;
import com.fithub.services.auth.api.exception.ApiException;
import com.fithub.services.auth.api.model.coach.ClientCapacityUpdateRequest;

public interface CoachService {

    List<CoachLoV> getAvailableCoaches();

    CoachResponse getCoachById(Long id) throws ApiException;

    void updateClientCapacity(ClientCapacityUpdateRequest clientCapacityUpdateRequest) throws Exception;

}