package com.fithub.services.auth.dao.seed;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.auth.dao.model.ClientEntity;
import com.fithub.services.auth.dao.model.CoachEntity;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.dao.repository.ClientRepository;
import com.fithub.services.auth.dao.repository.CoachRepository;
import com.fithub.services.auth.dao.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seed();
    }

    private void seed() {
        UserEntity coachJohnUserEntity = new UserEntity();
        UserEntity coachAlbertUserEntity = new UserEntity();
        UserEntity clientUserEntity = new UserEntity();
        if (userRepository.findAll().isEmpty()) {
            coachJohnUserEntity.setUuid("john-doe-coach");
            coachJohnUserEntity.setFirstName("John");
            coachJohnUserEntity.setLastName("Doe");
            userRepository.save(coachJohnUserEntity);

            coachAlbertUserEntity.setUuid("albert-johnson-coach");
            coachAlbertUserEntity.setFirstName("Albert");
            coachAlbertUserEntity.setLastName("Johnson");
            userRepository.save(coachAlbertUserEntity);

            clientUserEntity.setUuid("mary-ann-client");
            clientUserEntity.setFirstName("Mary");
            clientUserEntity.setLastName("Ann");
            userRepository.save(clientUserEntity);
        }

        
    }

}