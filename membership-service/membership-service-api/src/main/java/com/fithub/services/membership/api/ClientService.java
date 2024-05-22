package com.fithub.services.membership.api;

import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.rabbitmq.ClientRegistrationMessage;

public interface ClientService {

    void addClient(final ClientRegistrationMessage clientRegistrationMessage) throws ApiException;

}