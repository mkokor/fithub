package com.fithub.services.chat.test.suites;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.chat.api.UserService;
import com.fithub.services.chat.core.impl.UserServiceImpl;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.mapper.UserMapper;
import com.fithub.services.chat.test.configuration.BasicTestConfiguration;
import com.fithub.services.chat.api.model.user.UserResponse;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.ClientEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;

public class UserServiceTest extends BasicTestConfiguration {

    @Autowired
    private UserMapper userMapper;

    private ChatroomRepository chatroomRepository;

    private UserService userService;

    @BeforeMethod
    public void beforeMethod() {
        chatroomRepository = Mockito.mock(ChatroomRepository.class);

        userService = new UserServiceImpl(chatroomRepository, userMapper);
    }

    @Test
    public void testGetChatroomParticipants_ValidChatroomIdIsProvided_ReturnsParticipants() {
       try {
            UserEntity userEntity = new UserEntity();
            userEntity.setUuid("user-id");
            userEntity.setUsername("user123");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            coachEntity.setUser(userEntity);

            UserEntity clientUser = new UserEntity();
            clientUser.setUuid("client-id");
            clientUser.setUsername("user456");

            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setId(1L);
            clientEntity.setUser(clientUser);
            clientEntity.setCoach(coachEntity);
            clientUser.setClient(clientEntity);

            ChatroomEntity chatroomEntity = new ChatroomEntity();
            chatroomEntity.setId(1L);
            chatroomEntity.setAdmin(coachEntity);
            chatroomEntity.setRoomName("chatroom1");
            
            List<ClientEntity> clientEntities = new ArrayList<>();
            clientEntities.add(clientEntity);
            coachEntity.setClients(clientEntities);
            
            UserResponse userResponse1 = new UserResponse();
            userResponse1.setUuid("client-id");
            userResponse1.setUsername("user456"); 
           
            UserResponse userResponse2 = new UserResponse();
            userResponse2.setUuid("user-id");
            userResponse2.setUsername("user123"); 
            
            List<UserResponse> expectedResponse = new ArrayList<>();
            expectedResponse.add(userResponse1);
            expectedResponse.add(userResponse2);

            Mockito.when(chatroomRepository.findById(chatroomEntity.getId())).thenReturn(Optional.of(chatroomEntity));

            List<UserResponse> actualResponse = userService.getChatroomParticipants(chatroomEntity.getId());

            Assertions.assertThat(actualResponse).hasSameElementsAs(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        } 

    }

} 
