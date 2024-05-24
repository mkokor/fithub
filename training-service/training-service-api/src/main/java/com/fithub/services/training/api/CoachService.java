package com.fithub.services.training.api;

import com.fithub.services.training.api.model.GenericResponse;
import com.fithub.services.training.api.model.coach.ClientCapacityUpdateRequest;

public interface CoachService {

    GenericResponse updateClientCapacity(ClientCapacityUpdateRequest clientCapacityUpdateRequest) throws Exception;

}