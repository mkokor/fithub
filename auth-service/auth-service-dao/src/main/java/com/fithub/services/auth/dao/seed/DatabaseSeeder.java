package com.fithub.services.auth.dao.seed;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.auth.dao.model.CoachEntity;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.dao.repository.CoachRepository;
import com.fithub.services.auth.dao.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seed();
    }

    private void seed() {
        UserEntity coachJohnUserEntity = new UserEntity();

        if (userRepository.findAll().isEmpty()) {
            coachJohnUserEntity.setUuid("john-doe-coach");
            coachJohnUserEntity.setFirstName("John");
            coachJohnUserEntity.setLastName("Doe");
            coachJohnUserEntity.setUsername("johndoe");
            coachJohnUserEntity.setEmail("johndoe@email");
            coachJohnUserEntity.setEmailConfirmed(true);

            final String passwordHash = BCrypt.hashpw("password123#", BCrypt.gensalt());
            coachJohnUserEntity.setPasswordHash(passwordHash);

            userRepository.save(coachJohnUserEntity);

            CoachEntity coachJohnEntity = new CoachEntity();
            coachJohnEntity.setUser(coachJohnUserEntity);

            coachRepository.save(coachJohnEntity);
        }
    }

}