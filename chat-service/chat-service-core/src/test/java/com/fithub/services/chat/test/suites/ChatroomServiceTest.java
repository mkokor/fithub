package com.fithub.services.chat.test.suites;


import static org.testng.Assert.assertThrows;

import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.api.exception.BadRequestException;
import com.fithub.services.chat.api.model.chatroom.NewChatroomRequest;
import com.fithub.services.chat.core.impl.ChatroomServiceImpl;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.dao.repository.CoachRepository;
import com.fithub.services.chat.dao.repository.UserRepository;
import com.fithub.services.chat.mapper.ChatroomMapper;
import com.fithub.services.chat.mapper.MessageMapper;
import com.fithub.services.chat.mapper.UserMapper;
import com.fithub.services.chat.test.configuration.BasicTestConfiguration;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

public class ChatroomServiceTest extends BasicTestConfiguration {

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private ChatroomMapper chatroomMapper;
    @Autowired
    private UserMapper userMapper;

    private ChatroomRepository chatroomRepository;
    private CoachRepository coachRepository;
    private UserRepository userRepository;

    private ChatroomService chatroomService;
    
    private Validator validator;

    @BeforeMethod
    public void beforeMethod() {
        chatroomRepository = Mockito.mock(ChatroomRepository.class);
        coachRepository = Mockito.mock(CoachRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        
        
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;

        chatroomService = new ChatroomServiceImpl(chatroomRepository, coachRepository, userRepository, messageMapper, chatroomMapper, userMapper, validator);
    }
      
    @Test
    public void testCreateNewChatroom_InvalidDataIsProvided_ThrowsException() {
        try {
        	NewChatroomRequest newChatroomRequest = new NewChatroomRequest();
        	newChatroomRequest.setRoomName("t");;
            newChatroomRequest.setAdminId(1L);

            assertThrows(ConstraintViolationException.class, () -> {
                chatroomService.createNewChatroom(newChatroomRequest);
            });
        } catch (Exception exception) {
            Assert.fail();
        }
    }  
    
    @Test
    public void testCreateNewChatroom_ChatroomForCoachAlreadyExists_ThrowsBadRequestException() {
        try {
        	UserEntity userEntity = new UserEntity();
        	userEntity.setUsername("maryann");
        	userEntity.setUuid("coach-id");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            coachEntity.setUser(userEntity);
            
            userEntity.setCoach(coachEntity);
            
        	NewChatroomRequest newChatroomRequest = new NewChatroomRequest();
        	newChatroomRequest.setRoomName("New Chatroom");
            newChatroomRequest.setAdminId(1L);
            
            ChatroomEntity chatroomEntity = new ChatroomEntity();
            chatroomEntity.setAdmin(coachEntity);
            chatroomEntity.setRoomName("New Chatroom");
            
            coachEntity.setChatroom(chatroomEntity);
            
            Mockito.when(userRepository.findById(userEntity.getUuid())).thenReturn(Optional.of(userEntity));
            Mockito.when(coachRepository.findById(coachEntity.getId())).thenReturn(Optional.of(coachEntity));
            Mockito.when(chatroomRepository.findByCoachId(coachEntity.getId())).thenReturn(chatroomEntity);

            assertThrows(BadRequestException.class, () -> chatroomService.createNewChatroom(newChatroomRequest));
        } catch (Exception exception) {
            Assert.fail();
        }
    } 

}
