package com.fithub.services.membership.api;

import java.util.List;

import com.fithub.services.membership.api.model.client.ClientResponse;

public interface CoachService {

    public List<ClientResponse> getClients(Long coachId) throws Exception;

}