package com.fithub.services.training.core.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fithub.services.training.api.CoachService;
import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.model.GenericResponse;
import com.fithub.services.training.api.model.coach.ClientCapacityUpdateRequest;
import com.fithub.services.training.api.rabbitmq.CoachCapacityUpdateMessage;
import com.fithub.services.training.api.rabbitmq.EventType;
import com.fithub.services.training.core.context.UserContext;
import com.fithub.services.training.core.utils.RabbitMQHelper;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.CoachRepository;
import com.fithub.services.training.dao.repository.UserRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final RabbitMQHelper rabbitMQHelper;
    private final CoachRepository coachRepository;
    private final UserRepository userRepository;
    private final Validator validator;

    @Override
    public void updateClientCapacity(String coachUuid, Integer capacityValue) throws ApiException {
        Optional<UserEntity> coachUserEntity = userRepository.findById(coachUuid);
        if (coachUserEntity.isEmpty() || Objects.isNull(coachUserEntity.get().getCoach())) {
            throw new NotFoundException("The coach with provided UUID could not be found.");
        }

        CoachEntity coachEntity = coachUserEntity.get().getCoach();
        coachEntity.setClientCapacity(capacityValue);

        coachRepository.save(coachEntity);

    }

    @Override
    public GenericResponse updateClientCapacity(ClientCapacityUpdateRequest clientCapacityUpdateRequest) throws Exception {
        Set<ConstraintViolation<ClientCapacityUpdateRequest>> violations = validator.validate(clientCapacityUpdateRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        UserEntity coachUserEntity = UserContext.getCurrentContext().getUser();
        CoachEntity coachEntity = coachUserEntity.getCoach();

        Integer currentMaximumNumberOfClients = coachEntity.getClientCapacity();
        Integer requestedNumberOfClients = clientCapacityUpdateRequest.getNewClientCapacity();
        Integer currentNumberOfClients = coachRepository.getNumberOfClients(coachEntity.getId());

        if (currentNumberOfClients > requestedNumberOfClients) {
            throw new BadRequestException("The maximum number of client cannot be lower than the current number of clients.");
        }

        coachEntity.setClientCapacity(requestedNumberOfClients);
        coachRepository.save(coachEntity);

        CoachCapacityUpdateMessage coachCapacityUpdateMessage = new CoachCapacityUpdateMessage();
        coachCapacityUpdateMessage.setUpdateEventType(EventType.PENDING.toString());
        coachCapacityUpdateMessage.setCoachUuid(coachUserEntity.getUuid());
        coachCapacityUpdateMessage.setOldCapacityValue(currentMaximumNumberOfClients);
        coachCapacityUpdateMessage.setNewCapacityValue(coachEntity.getClientCapacity());
        rabbitMQHelper.sendCoachCapacityUpdateEventToQueue(coachCapacityUpdateMessage);

        GenericResponse response = new GenericResponse();
        response.setMessage("The client capacity update request is pending. Email notification will be sent once it is complete.");
        return response;
    }

}