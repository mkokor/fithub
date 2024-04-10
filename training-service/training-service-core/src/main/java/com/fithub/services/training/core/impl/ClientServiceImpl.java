package com.fithub.services.training.core.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fithub.services.training.api.ClientService;
import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.model.client.CoachChangeRequest;
import com.fithub.services.training.api.model.client.CoachChangeResponse;
import com.fithub.services.training.core.client.MembershipServiceClient;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.UserRepository;
import com.fithub.services.training.mapper.ClientMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final MembershipServiceClient membershipServiceClient;
    private final UserRepository userRepository;
    private final ClientMapper clientMapper;
    private final Validator validator;

    private UserEntity getAccessTokenOwner() {
        return userRepository.findById("mary-ann-client").get();
    }

    @Override
    public CoachChangeResponse changeCoach(CoachChangeRequest coachChangeRequest) throws ApiException {
        Set<ConstraintViolation<CoachChangeRequest>> violations = validator.validate(coachChangeRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // The endpoint will be forbidden for the coaches.
        UserEntity client = getAccessTokenOwner();

        if (membershipServiceClient.getMembershipPaymentReport(client.getUuid()).getBody().getHasDebt()) {
            throw new BadRequestException("The action could not be performed because the client has membership debt.");
        }

        Optional<UserEntity> newCoach = userRepository.findById(coachChangeRequest.getCoachUuid());
        if (newCoach.isEmpty()) {
            throw new NotFoundException("The coach with provied UUID could not be found.");
        }
        if (newCoach.get().getCoach() == null) {
            throw new BadRequestException("The provided UUID is associated with a client entity.");
        }

        client.getClient().setCoach(newCoach.get().getCoach());
        userRepository.save(client);

        return clientMapper.clientEntityToCoachChangeResponse(client.getClient());
    }

}