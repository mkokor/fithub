package com.fithub.services.mealplan.api;

import com.fithub.services.mealplan.api.exception.ApiException;
import com.fithub.services.mealplan.api.rabbitmq.ClientRegistrationMessage;

public interface ClientService {

    void addClient(final ClientRegistrationMessage clientRegistrationMessage) throws ApiException;

}