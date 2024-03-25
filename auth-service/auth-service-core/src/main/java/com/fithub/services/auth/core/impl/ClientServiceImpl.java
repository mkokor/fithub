package com.fithub.services.auth.core.impl;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fithub.services.auth.api.ClientService;
import com.fithub.services.auth.api.exception.BadRequestException;
import com.fithub.services.auth.api.exception.NotFoundException;
import com.fithub.services.auth.api.model.client.ClientResponse;
import com.fithub.services.auth.api.model.client.ClientSignUpRequest;
import com.fithub.services.auth.dao.model.ClientEntity;
import com.fithub.services.auth.dao.model.CoachEntity;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.dao.repository.ClientRepository;
import com.fithub.services.auth.dao.repository.CoachRepository;
import com.fithub.services.auth.dao.repository.UserRepository;
import com.fithub.services.auth.mapper.ClientMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final CoachRepository coachRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final ClientMapper clientMapper;
    private final Validator validator;

    @Override
    public ClientResponse signUp(ClientSignUpRequest clientSignUpRequest) throws Exception {
        final Optional<CoachEntity> coach = coachRepository.findById(clientSignUpRequest.getCoachId());
        Set<ConstraintViolation<ClientSignUpRequest>> violations = validator.validate(clientSignUpRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        if (!coach.isPresent()) {
            throw new NotFoundException("The coach with provided ID could not be found.");
        }

        if (userRepository.existsByEmail(clientSignUpRequest.getEmail())) {
            throw new BadRequestException("The provided email is already registered.");
        }
        if (userRepository.existsByUsername(clientSignUpRequest.getUsername())) {
            throw new BadRequestException("The provided username is already registered.");
        }

        final UserEntity newUser = new UserEntity();
        newUser.setUuid(UUID.randomUUID().toString());
        newUser.setFirstName(clientSignUpRequest.getFirstName());
        newUser.setLastName(clientSignUpRequest.getLastName());
        newUser.setUsername(clientSignUpRequest.getUsername());
        newUser.setEmail(clientSignUpRequest.getEmail());
        newUser.setPasswordHash(clientSignUpRequest.getPassword());

        final ClientEntity newClient = new ClientEntity();
        newClient.setUser(newUser);
        newClient.setCoach(coach.get());

        userRepository.save(newUser);
        clientRepository.save(newClient);

        return clientMapper.entityToDto(newClient);
    }

}