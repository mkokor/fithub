package com.fithub.services.training.api;

import java.util.List;

import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.model.GenericResponse;
import com.fithub.services.training.api.model.client.ClientResponse;
import com.fithub.services.training.api.model.coach.ClientCapacityUpdateRequest;

public interface CoachService {

    void updateClientCapacity(final String coachUuid, final Integer capacityValue) throws ApiException;

    GenericResponse updateClientCapacity(ClientCapacityUpdateRequest clientCapacityUpdateRequest) throws Exception;

    List<ClientResponse> getClients();

}