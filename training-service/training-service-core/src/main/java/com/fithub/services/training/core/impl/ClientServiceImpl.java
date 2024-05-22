package com.fithub.services.training.core.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fithub.services.systemevents.ActionLogRequest;
import com.fithub.services.systemevents.ActionLoggerServiceGrpc;
import com.fithub.services.systemevents.VoidResponse;
import com.fithub.services.training.api.ClientService;
import com.fithub.services.training.api.enums.ActionType;
import com.fithub.services.training.api.enums.ResourceType;
import com.fithub.services.training.api.enums.ResponseType;
import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.model.client.CoachChangeRequest;
import com.fithub.services.training.api.model.client.CoachChangeResponse;
import com.fithub.services.training.api.rabbitmq.ClientRegistrationMessage;
import com.fithub.services.training.core.client.MembershipServiceClient;
import com.fithub.services.training.dao.model.ClientEntity;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.ClientRepository;
import com.fithub.services.training.dao.repository.UserRepository;
import com.fithub.services.training.mapper.ClientMapper;

import io.grpc.stub.StreamObserver;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final MembershipServiceClient membershipServiceClient;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final Validator validator;

    @Value("${spring.application.name}")
    private String microserviceName;

    @GrpcClient("system-events")
    private ActionLoggerServiceGrpc.ActionLoggerServiceStub systemEventsClient;

    private void sendActionLogRequest(ActionType actionType, ResponseType responseType) {
        ActionLogRequest actionLogRequest = ActionLogRequest.newBuilder().setMicroserviceName(microserviceName)
                .setActionType(actionType.toString()).setResourceTitle(ResourceType.CLIENT.toString())
                .setResponseType(responseType.toString()).build();

        systemEventsClient.logAction(actionLogRequest, new StreamObserver<VoidResponse>() {

            @Override
            public void onNext(VoidResponse response) {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
            }

        });
    }

    private UserEntity getAccessTokenOwner() {
        return userRepository.findById("mary-ann-client").get();
    }

    @Override
    public CoachChangeResponse changeCoach(CoachChangeRequest coachChangeRequest) throws ApiException {
        Set<ConstraintViolation<CoachChangeRequest>> violations = validator.validate(coachChangeRequest);
        if (!violations.isEmpty()) {
            sendActionLogRequest(ActionType.UPDATE, ResponseType.ERROR);
            throw new ConstraintViolationException(violations);
        }

        // The endpoint will be forbidden for the coaches.
        UserEntity client = getAccessTokenOwner();

        if (membershipServiceClient.getMembershipPaymentReport(client.getUuid()).getBody().getHasDebt()) {
            sendActionLogRequest(ActionType.UPDATE, ResponseType.ERROR);
            throw new BadRequestException("The action could not be performed because the client has membership debt.");
        }

        Optional<UserEntity> newCoach = userRepository.findById(coachChangeRequest.getCoachUuid());
        if (newCoach.isEmpty()) {
            sendActionLogRequest(ActionType.UPDATE, ResponseType.ERROR);
            throw new NotFoundException("The coach with provied UUID could not be found.");
        }
        if (newCoach.get().getCoach() == null) {
            sendActionLogRequest(ActionType.UPDATE, ResponseType.ERROR);
            throw new BadRequestException("The provided UUID is associated with a client entity.");
        }

        client.getClient().setCoach(newCoach.get().getCoach());
        userRepository.save(client);

        sendActionLogRequest(ActionType.UPDATE, ResponseType.SUCCESS);
        return clientMapper.clientEntityToCoachChangeResponse(client.getClient());
    }

    @Override
    public void addClient(ClientRegistrationMessage clientRegistrationMessage) throws ApiException {
        Optional<UserEntity> coachUserEntity = userRepository.findById(clientRegistrationMessage.getCoachUuid());
        if (coachUserEntity.isEmpty() || coachUserEntity.get().getCoach() == null) {
            throw new NotFoundException("The coach with provided ID could not be found.");
        }
        final CoachEntity coachEntity = coachUserEntity.get().getCoach();

        UserEntity newClientUserEntity = new UserEntity();
        newClientUserEntity.setUuid(clientRegistrationMessage.getUuid());
        newClientUserEntity.setFirstName(clientRegistrationMessage.getFirstName());
        newClientUserEntity.setLastName(clientRegistrationMessage.getLastName());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setUser(newClientUserEntity);
        clientEntity.setCoach(coachEntity);

        userRepository.save(newClientUserEntity);
        clientRepository.save(clientEntity);

        newClientUserEntity.setClient(clientEntity);
        userRepository.save(newClientUserEntity);
    }

}