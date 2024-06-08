package com.fithub.services.membership.core.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.membership.api.ClientService;
import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.exception.NotFoundException;
import com.fithub.services.membership.api.rabbitmq.ClientRegistrationMessage;
import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.model.CoachEntity;
import com.fithub.services.membership.dao.model.UserEntity;
import com.fithub.services.membership.dao.repository.ClientRepository;
import com.fithub.services.membership.dao.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

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