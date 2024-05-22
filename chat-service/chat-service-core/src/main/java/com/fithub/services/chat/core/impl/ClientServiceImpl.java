package com.fithub.services.chat.core.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.chat.api.ClientService;
import com.fithub.services.chat.api.exception.ApiException;
import com.fithub.services.chat.api.exception.NotFoundException;
import com.fithub.services.chat.api.rabbitmq.ClientRegistrationMessage;
import com.fithub.services.chat.dao.model.ClientEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ClientRepository;
import com.fithub.services.chat.dao.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    @Override
    public void addClient(ClientRegistrationMessage clientRegistrationMessage) throws ApiException {
        Optional<UserEntity> coachUserEntity = userRepository.findById(clientRegistrationMessage.getCoachUuid());
        if (coachUserEntity.isEmpty() || coachUserEntity.get().getCoach() == null) {
            throw new NotFoundException("The coach with provided ID could not be found.");
        }
        final CoachEntity coachEntity = coachUserEntity.get().getCoach();

        UserEntity newClientUserEntity = new UserEntity();
        newClientUserEntity.setUuid(clientRegistrationMessage.getUuid());
        newClientUserEntity.setUsername(clientRegistrationMessage.getUsername());

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setUser(newClientUserEntity);
        clientEntity.setCoach(coachEntity);

        userRepository.save(newClientUserEntity);
        clientRepository.save(clientEntity);

        newClientUserEntity.setClient(clientEntity);
        userRepository.save(newClientUserEntity);
    }

}