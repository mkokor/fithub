package com.fithub.services.auth.core.utils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fithub.services.auth.api.rabbitmq.ClientRegistrationMessage;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitMQHelper {

    @Value("${rabbitmq.queue.client-registration}")
    private String clientRegistrationQueueTitle;

    @Value("${rabbitmq.exchange.direct}")
    private String directExchangeTitle;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final UserMapper userMapper;

    private <T> String writeObjectAsJsonString(final T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public void sendUserRegistrationEventToQueue(final UserEntity newClient) throws JsonProcessingException {
        final ClientRegistrationMessage clientRegistrationMessage = userMapper.entityToClientRegistrationMessage(newClient);

        final String newClientJsonString = writeObjectAsJsonString(clientRegistrationMessage);
        rabbitTemplate.convertAndSend(directExchangeTitle, clientRegistrationQueueTitle, newClientJsonString);
    }

}