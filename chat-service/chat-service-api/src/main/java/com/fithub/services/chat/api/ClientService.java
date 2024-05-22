package com.fithub.services.chat.api;

import com.fithub.services.chat.api.exception.ApiException;
import com.fithub.services.chat.api.rabbitmq.ClientRegistrationMessage;

public interface ClientService {

    void addClient(final ClientRegistrationMessage clientRegistrationMessage) throws ApiException;

}