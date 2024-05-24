package com.fithub.services.training.dao.seed;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.CoachRepository;
import com.fithub.services.training.dao.repository.UserRepository;

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
        if (userRepository.findAll().isEmpty()) {
            UserEntity coachJohnUserEntity = new UserEntity();
            coachJohnUserEntity.setUuid("john-doe-coach");
            coachJohnUserEntity.setFirstName("John");
            coachJohnUserEntity.setLastName("Doe");

            userRepository.save(coachJohnUserEntity);

            CoachEntity coachJohnEntity = new CoachEntity();
            coachJohnEntity.setClientCapacity(3);
            coachJohnEntity.setUser(coachJohnUserEntity);

            coachRepository.save(coachJohnEntity);

            UserEntity coachMaryUserEntity = new UserEntity();
            coachMaryUserEntity.setUuid("mary-ann-coach");
            coachMaryUserEntity.setFirstName("Mary");
            coachMaryUserEntity.setLastName("Ann");

            userRepository.save(coachMaryUserEntity);

            CoachEntity coachMaryEntity = new CoachEntity();
            coachMaryEntity.setClientCapacity(3);
            coachMaryEntity.setUser(coachMaryUserEntity);

            coachRepository.save(coachMaryEntity);

            UserEntity coachJamesUserEntity = new UserEntity();
            coachJamesUserEntity.setUuid("james-martin-coach");
            coachJamesUserEntity.setFirstName("James");
            coachJamesUserEntity.setLastName("Martin");

            userRepository.save(coachJamesUserEntity);

            CoachEntity coachJamesEntity = new CoachEntity();
            coachJamesEntity.setClientCapacity(20);
            coachJamesEntity.setUser(coachJamesUserEntity);

            coachRepository.save(coachJamesEntity);
        }
    }

}