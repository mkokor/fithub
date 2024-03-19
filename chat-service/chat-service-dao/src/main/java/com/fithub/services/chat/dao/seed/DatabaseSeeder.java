package com.fithub.services.chat.dao.seed;

import java.util.UUID;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.MessageRepository;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.dao.repository.ClientRepository;
import com.fithub.services.chat.dao.repository.CoachRepository;
import com.fithub.services.chat.dao.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final MessageRepository messageRepository;
    private final ChatroomRepository chatroomRepository;
    

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seed();
    }

    private void seed() {
        UserEntity userEntity = new UserEntity();
        if (userRepository.findAll().isEmpty()) {
            userEntity.setUuid(UUID.randomUUID().toString());
            userEntity.setUsername("johndoe");
            userRepository.save(userEntity);
        }

        CoachEntity coachEntity = new CoachEntity();
        if (coachRepository.findAll().isEmpty()) {
            coachEntity.setUser(userEntity);
            coachRepository.save(coachEntity);
        }
    }
}