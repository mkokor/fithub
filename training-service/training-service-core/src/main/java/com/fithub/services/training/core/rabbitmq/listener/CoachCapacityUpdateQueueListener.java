package com.fithub.services.training.core.rabbitmq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fithub.services.training.api.CoachService;
import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.model.external.EmailSendMessageRequest;
import com.fithub.services.training.api.rabbitmq.CoachCapacityUpdateMessage;
import com.fithub.services.training.api.rabbitmq.EventType;
import com.fithub.services.training.core.client.AuthServiceClient;
import com.fithub.services.training.core.utils.FeignClientHelper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CoachCapacityUpdateQueueListener {

    private final CoachService coachService;
    private final AuthServiceClient authServiceClient;
    private final FeignClientHelper feignClientApiHelper;
    private final ObjectMapper objectMapper;

    private CoachCapacityUpdateMessage readJsonStringToCoachCapacityUpdateMessage(final String jsonString) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, CoachCapacityUpdateMessage.class);
    }

    private void sendCoachCapacityUpdateResultEmail(final String recipientUuid, final String message) throws ApiException {
        final EmailSendMessageRequest emailSendMessageRequest = new EmailSendMessageRequest();
        emailSendMessageRequest.setRecipientUuid(recipientUuid);
        emailSendMessageRequest.setSubject("Coach Capacity Update Result");
        emailSendMessageRequest.setContent(message);

        feignClientApiHelper.callApi((() -> authServiceClient.sendFitHubEmailMessage(emailSendMessageRequest)));
    }

    @RabbitListener(queues = "${rabbitmq.queue.coach-capacity-update-training}")
    public void handleMessage(final String message) throws JsonProcessingException, ApiException {
        CoachCapacityUpdateMessage coachCapacityUpdateMessage = readJsonStringToCoachCapacityUpdateMessage(message);

        String coachCapacityUpdateResultMessage = "The capacity was successfully updated.";

        if (EventType.ERROR.getValue().equals(coachCapacityUpdateMessage.getUpdateEventType())) {
            coachService.updateClientCapacity(coachCapacityUpdateMessage.getCoachUuid(), coachCapacityUpdateMessage.getOldCapacityValue());
            coachCapacityUpdateResultMessage = "The capacity update could not be performed.";
        }

        sendCoachCapacityUpdateResultEmail(coachCapacityUpdateMessage.getCoachUuid(), coachCapacityUpdateResultMessage);
    }

}