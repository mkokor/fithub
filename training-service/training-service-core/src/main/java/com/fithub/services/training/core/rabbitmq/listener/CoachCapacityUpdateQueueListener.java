package com.fithub.services.training.core.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fithub.services.training.api.CoachService;
import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.rabbitmq.CoachCapacityUpdateMessage;
import com.fithub.services.training.api.rabbitmq.EventType;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CoachCapacityUpdateQueueListener {

    private final CoachService coachService;
    private final ObjectMapper objectMapper;

    private CoachCapacityUpdateMessage readJsonStringToCoachCapacityUpdateMessage(final String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, CoachCapacityUpdateMessage.class);
    }

    @RabbitListener(queues = "${rabbitmq.queue.coach-capacity-update-training}")
    public void handleMessage(final String message) throws JsonProcessingException, ApiException {
        CoachCapacityUpdateMessage coachCapacityUpdateMessage = readJsonStringToCoachCapacityUpdateMessage(message);

        if (EventType.ERROR.getValue().equals(coachCapacityUpdateMessage.getUpdateEventType())) {
            coachService.updateClientCapacity(coachCapacityUpdateMessage.getCoachUuid(), coachCapacityUpdateMessage.getOldCapacityValue());
        } else if (EventType.SUCCESS.getValue().equals(coachCapacityUpdateMessage.getUpdateEventType())) {
            // Ok
        }
    }

}