package com.fithub.services.training.api;

import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.rabbitmq.ClientRegistrationMessage;

public interface ClientService {

    void addClient(final ClientRegistrationMessage clientRegistrationMessage) throws ApiException;

}