package com.fithub.services.mealplan.core.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fithub.services.mealplan.api.ClientService;
import com.fithub.services.mealplan.api.exception.ApiException;
import com.fithub.services.mealplan.api.rabbitmq.ClientRegistrationMessage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClientRegistrationQueueListener {

    private final ClientService clientService;
    private final ObjectMapper objectMapper;

    private ClientRegistrationMessage readJsonStringToClientRegistrationMessage(final String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, ClientRegistrationMessage.class);
    }

    @RabbitListener(queues = "${rabbitmq.queue.client-registration-mealplan}")
    public void handleMessage(final String message) throws JsonProcessingException, ApiException {
        ClientRegistrationMessage clientRegistrationMessage = readJsonStringToClientRegistrationMessage(message);

        clientService.addClient(clientRegistrationMessage);
    }

}