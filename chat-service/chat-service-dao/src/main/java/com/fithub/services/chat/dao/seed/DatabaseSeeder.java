package com.fithub.services.chat.dao.seed;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.dao.repository.CoachRepository;
import com.fithub.services.chat.dao.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final CoachRepository coachRepository;
    private final UserRepository userRepository;
    private final ChatroomRepository chatroomRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        seed();
    }

    private void seed() {
        if (userRepository.findAll().isEmpty()) {
            UserEntity coachJohnUserEntity = new UserEntity();
            coachJohnUserEntity.setUuid("john-doe-coach");
            coachJohnUserEntity.setUsername("johndoe");

            userRepository.save(coachJohnUserEntity);

            CoachEntity coachJohnEntity = new CoachEntity();
            coachJohnEntity.setUser(coachJohnUserEntity);

            coachRepository.save(coachJohnEntity);

            ChatroomEntity chatroomJohnEntity = new ChatroomEntity();
            chatroomJohnEntity.setAdmin(coachJohnEntity);
            chatroomJohnEntity.setRoomName("John's Room");
            chatroomRepository.save(chatroomJohnEntity);

            UserEntity coachMaryUserEntity = new UserEntity();
            coachMaryUserEntity.setUuid("mary-ann-coach");
            coachMaryUserEntity.setUsername("maryann");

            userRepository.save(coachMaryUserEntity);

            CoachEntity coachMaryEntity = new CoachEntity();
            coachMaryEntity.setUser(coachMaryUserEntity);

            coachRepository.save(coachMaryEntity);

            ChatroomEntity chatroomMaryEntity = new ChatroomEntity();
            chatroomMaryEntity.setAdmin(coachMaryEntity);
            chatroomMaryEntity.setRoomName("Mary's Room");
            chatroomRepository.save(chatroomMaryEntity);

            UserEntity coachJamesUserEntity = new UserEntity();
            coachJamesUserEntity.setUuid("james-martin-coach");
            coachJamesUserEntity.setUsername("jamesmartin");

            userRepository.save(coachJamesUserEntity);

            CoachEntity coachJamesEntity = new CoachEntity();
            coachJamesEntity.setUser(coachJamesUserEntity);

            coachRepository.save(coachJamesEntity);

            ChatroomEntity chatroomJamesEntity = new ChatroomEntity();
            chatroomJamesEntity.setAdmin(coachJamesEntity);
            chatroomJamesEntity.setRoomName("Martin's Room");
            chatroomRepository.save(chatroomJamesEntity);
        }
    }

}