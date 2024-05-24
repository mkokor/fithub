package com.fithub.services.auth.core.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fithub.services.auth.api.CoachService;
import com.fithub.services.auth.api.exception.ApiException;
import com.fithub.services.auth.api.model.coach.ClientCapacityUpdateRequest;
import com.fithub.services.auth.api.rabbitmq.CoachCapacityUpdateMessage;
import com.fithub.services.auth.api.rabbitmq.EventType;
import com.fithub.services.auth.core.utils.RabbitMQHelper;
import com.fithub.services.auth.mapper.CoachMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CoachCapacityUpdateQueueListener {

    private final CoachService coachService;
    private final ObjectMapper objectMapper;
    private final CoachMapper coachMapper;
    private final RabbitMQHelper rabbitMQHelper;

    private CoachCapacityUpdateMessage readJsonStringToCoachCapacityUpdateMessage(final String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, CoachCapacityUpdateMessage.class);
    }

    private CoachCapacityUpdateMessage constructQueueMessage(final EventType eventType, final String coachUuid,
            final Integer oldCapacityValue, final Integer newCapacityValue) {
        CoachCapacityUpdateMessage coachCapacityUpdateMessage = new CoachCapacityUpdateMessage();

        coachCapacityUpdateMessage.setUpdateEventType(eventType.getValue());
        coachCapacityUpdateMessage.setCoachUuid(coachUuid);
        coachCapacityUpdateMessage.setOldCapacityValue(oldCapacityValue);
        coachCapacityUpdateMessage.setNewCapacityValue(newCapacityValue);

        return coachCapacityUpdateMessage;
    }

    @RabbitListener(queues = "${rabbitmq.queue.coach-capacity-update-auth}")
    public void handleMessage(final String message) throws JsonProcessingException, ApiException {
        CoachCapacityUpdateMessage coachCapacityUpdateMessage = readJsonStringToCoachCapacityUpdateMessage(message);

        EventType responseEventType = EventType.SUCCESS;
        try {
            ClientCapacityUpdateRequest clientCapacityUpdateRequest = coachMapper
                    .coachCapacityUpdateMessageToClientCapacityUpdateRequest(coachCapacityUpdateMessage);

            coachService.updateClientCapacity(clientCapacityUpdateRequest);
        } catch (Exception exception) {
            responseEventType = EventType.ERROR;
        } finally {
            CoachCapacityUpdateMessage responseMessage = constructQueueMessage(responseEventType, coachCapacityUpdateMessage.getCoachUuid(),
                    coachCapacityUpdateMessage.getOldCapacityValue(), coachCapacityUpdateMessage.getNewCapacityValue());

            rabbitMQHelper.sendCoachCapacityUpdateEventToQueue(responseMessage);
        }
    }

}