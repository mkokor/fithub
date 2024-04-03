package com.fithub.services.chat.dao.seed;

import java.util.UUID;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.ClientEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.MessageEntity;
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
        UserEntity userEntity1 = new UserEntity();
        UserEntity userEntity2 = new UserEntity();
        UserEntity userEntity3 = new UserEntity();
        UserEntity userEntity4 = new UserEntity();
        if (userRepository.findAll().isEmpty()) {
            userEntity1.setUuid(UUID.randomUUID().toString());
            userEntity1.setUsername("johndoe");
            userRepository.save(userEntity1);
        	userEntity2.setUuid(UUID.randomUUID().toString());
        	userEntity2.setUsername("evan123");
        	userRepository.save(userEntity2);
        	userEntity3.setUuid(UUID.randomUUID().toString());
        	userEntity3.setUsername("lea_43");
        	userRepository.save(userEntity3);
        	userEntity4.setUuid(UUID.randomUUID().toString());
        	userEntity4.setUsername("coach_98");
        	userRepository.save(userEntity4);
        }

        CoachEntity coachEntity = new CoachEntity();
        CoachEntity coachEntity2 = new CoachEntity();
        if (coachRepository.findAll().isEmpty()) {
            coachEntity.setUser(userEntity1);
            coachRepository.save(coachEntity);
            coachEntity2.setUser(userEntity4);
            coachRepository.save(coachEntity2);
        }
        
        ClientEntity clientEntity1 = new ClientEntity();
        ClientEntity clientEntity2 = new ClientEntity();
        if (clientRepository.findAll().isEmpty()) {
        	clientEntity1.setUser(userEntity2);
        	clientEntity1.setCoach(coachEntity);
        	clientRepository.save(clientEntity1);
        	clientEntity2.setUser(userEntity3);
        	clientEntity2.setCoach(coachEntity);
        	clientRepository.save(clientEntity2);
        }
 	
        ChatroomEntity chatroomEntity = new ChatroomEntity();
        if (chatroomRepository.findAll().isEmpty()) {
        	chatroomEntity.setRoomName("Chatroom");
        	chatroomEntity.setAdmin(coachEntity);
        	chatroomRepository.save(chatroomEntity);
        }
        
        MessageEntity messageEntity1 = new MessageEntity();
        if (messageRepository.findAll().isEmpty()) {
        	messageEntity1.setChatroom(chatroomEntity);
        	messageEntity1.setContent("See you tonight at 7:30");
        	messageEntity1.setUser(userEntity1);
        	messageRepository.save(messageEntity1);
        }
    }
}