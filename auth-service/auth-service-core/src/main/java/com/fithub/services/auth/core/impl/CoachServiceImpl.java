package com.fithub.services.auth.core.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fithub.services.auth.api.CoachService;
import com.fithub.services.auth.api.coach.CoachLoV;
import com.fithub.services.auth.api.coach.CoachResponse;
import com.fithub.services.auth.api.exception.ApiException;
import com.fithub.services.auth.api.exception.BadRequestException;
import com.fithub.services.auth.api.exception.NotFoundException;
import com.fithub.services.auth.api.model.coach.ClientCapacityUpdateRequest;
import com.fithub.services.auth.dao.model.CoachEntity;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.dao.repository.CoachRepository;
import com.fithub.services.auth.dao.repository.UserRepository;
import com.fithub.services.auth.mapper.CoachMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;
    private final CoachMapper coachMapper;
    private final Validator validator;

    @Override
    public List<CoachLoV> getAvailableCoaches() {
        return coachRepository.findAvailableCoaches();
    }

    @Override
    public CoachResponse getCoachById(Long id) throws NotFoundException {
        Optional<CoachEntity> coachEntity = coachRepository.findById(id);
        if (coachEntity.isEmpty()) {
            throw new NotFoundException("The coach with the provided ID could not be found.");
        }

        return coachMapper.entityToCoachResponse(coachEntity.get());
    }

    @Override
    public void updateClientCapacity(ClientCapacityUpdateRequest clientCapacityUpdateRequest) throws ApiException {
        Set<ConstraintViolation<ClientCapacityUpdateRequest>> violations = validator.validate(clientCapacityUpdateRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Optional<UserEntity> coachUser = userRepository.findById(clientCapacityUpdateRequest.getCoachUuid());
        if (coachUser.isEmpty()) {
            throw new NotFoundException("The user with provided UUID could not be found.");
        }

        UserEntity coachUserEntity = coachUser.get();
        if (Objects.isNull(coachUserEntity.getCoach())) {
            throw new BadRequestException("The provided UUID is not associated with coach user.");
        }

        CoachEntity coachEntity = coachUserEntity.getCoach();

        Integer currentNumberOfClients = coachRepository.getNumberOfClients(coachEntity.getId());
        if (currentNumberOfClients > clientCapacityUpdateRequest.getNewClientCapacity()) {
            throw new BadRequestException("The maximum number of client cannot be lower than the current number of clients.");
        }

        coachEntity.setClientCapacity(clientCapacityUpdateRequest.getNewClientCapacity());
        coachRepository.save(coachEntity);
    }

}