package com.fithub.services.training.core.utils;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fithub.services.training.api.rabbitmq.CoachCapacityUpdateMessage;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitMQHelper {

    @Value("${rabbitmq.queue.coach-capacity-update-auth}")
    private String coachCapacityUpdateAuthQueueTitle;

    @Value("${rabbitmq.exchange.direct}")
    private String directExchangeTitle;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    private <T> String writeObjectAsJsonString(final T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public void sendCoachCapacityUpdateEventToQueue(CoachCapacityUpdateMessage coachCapacityUpdateMessage) throws JsonProcessingException {
        final String messageJsonString = writeObjectAsJsonString(coachCapacityUpdateMessage);

        rabbitTemplate.convertAndSend(directExchangeTitle, coachCapacityUpdateAuthQueueTitle, messageJsonString);
    }

}