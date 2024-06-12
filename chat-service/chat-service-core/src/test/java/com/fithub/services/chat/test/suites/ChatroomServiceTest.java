package com.fithub.services.chat.test.suites;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.api.model.chatroom.ChatroomDataResponse;
import com.fithub.services.chat.api.model.chatroom.ChatroomResponse;
import com.fithub.services.chat.api.model.user.UserResponse;
import com.fithub.services.chat.core.context.UserContext;
import com.fithub.services.chat.core.impl.ChatroomServiceImpl;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.ClientEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.mapper.ChatroomMapper;
import com.fithub.services.chat.mapper.UserMapper;
import com.fithub.services.chat.test.configuration.BasicTestConfiguration;

public class ChatroomServiceTest extends BasicTestConfiguration {

    @Autowired
    private ChatroomMapper chatroomMapper;

    @Autowired
    private UserMapper userMapper;

    private ChatroomRepository chatroomRepository;

    private ChatroomService chatroomService;

    @BeforeMethod
    public void beforeMethod() {
        chatroomRepository = Mockito.mock(ChatroomRepository.class);

        chatroomService = new ChatroomServiceImpl(chatroomRepository, chatroomMapper, userMapper, null);
    }

    private UserEntity constructCoachUser() {
        UserEntity authenticatedCoach = new UserEntity();
        authenticatedCoach.setUuid("john-doe-coach");

        CoachEntity coachEntity = new CoachEntity();
        coachEntity.setId(1L);
        coachEntity.setUser(authenticatedCoach);

        authenticatedCoach.setCoach(coachEntity);

        return authenticatedCoach;
    }

    private void authenticateCoach() {
        UserEntity authenticatedCoach = constructCoachUser();

        UserContext userContext = new UserContext();
        userContext.setUser(authenticatedCoach);

        UserContext.setCurrentContext(userContext);
    }

    @Test
    public void testGetChatroomData() {
        try {
            authenticateCoach();
            UserEntity coachUser = constructCoachUser();

            UserEntity clientUser = new UserEntity();
            clientUser.setUuid("client-user");
            clientUser.setUsername("client");

            ClientEntity client = new ClientEntity();
            client.setCoach(coachUser.getCoach());
            client.setUser(clientUser);
            coachUser.getCoach().setClients(List.of(client));

            ChatroomEntity chatroomEntity = new ChatroomEntity();
            chatroomEntity.setAdmin(coachUser.getCoach());
            chatroomEntity.setId(1L);
            chatroomEntity.setRoomName("Test Room");

            UserResponse adminResponseExpected = new UserResponse();
            adminResponseExpected.setUsername(coachUser.getUsername());
            adminResponseExpected.setUuid(coachUser.getUuid());

            ChatroomResponse chatroomResponseExpected = new ChatroomResponse();
            chatroomResponseExpected.setId(chatroomEntity.getId());
            chatroomResponseExpected.setAdmin(adminResponseExpected);
            chatroomResponseExpected.setRoomName(chatroomEntity.getRoomName());

            List<UserResponse> participantsResponseExpected = new ArrayList<>();
            participantsResponseExpected.add(adminResponseExpected);

            UserResponse clientResponseExpected = new UserResponse();
            clientResponseExpected.setUsername(clientUser.getUsername());
            clientResponseExpected.setUuid(clientUser.getUuid());
            participantsResponseExpected.add(clientResponseExpected);

            ChatroomDataResponse chatroomDataResponseExpected = new ChatroomDataResponse();
            chatroomDataResponseExpected.setChatroomDetails(chatroomResponseExpected);
            chatroomDataResponseExpected.setParticipants(participantsResponseExpected);

            Mockito.when(chatroomRepository.findByCoachId(coachUser.getCoach().getId())).thenReturn(Optional.of(chatroomEntity));
            Mockito.when(chatroomRepository.findById(chatroomEntity.getId())).thenReturn(Optional.of(chatroomEntity));

            ChatroomDataResponse chatroomDataResponseActual = chatroomService.getChatroomData();

            Assert.assertEquals(chatroomDataResponseExpected, chatroomDataResponseActual);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}