package com.fithub.services.chat.test.suites;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.core.impl.ChatroomServiceImpl;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.mapper.ChatroomMapper;
import com.fithub.services.chat.mapper.MessageMapper;
import com.fithub.services.chat.mapper.UserMapper;
import com.fithub.services.chat.test.configuration.BasicTestConfiguration;
import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.ClientEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.MessageEntity;
import com.fithub.services.chat.dao.model.UserEntity;

public class ChatroomServiceTest extends BasicTestConfiguration {

    @Autowired
    private MessageMapper messageMapper;
    private ChatroomMapper chatroomMapper;
    private UserMapper userMapper;

    private ChatroomRepository chatroomRepository;

    private ChatroomService chatroomService;

    @BeforeMethod
    public void beforeMethod() {
        chatroomRepository = Mockito.mock(ChatroomRepository.class);

        chatroomService = new ChatroomServiceImpl(chatroomRepository, messageMapper, chatroomMapper, userMapper);
    }

    @Test
    public void testGetMessages_ValidChatroomIdIsProvided_ReturnsMessages() {
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
            
            LocalDateTime time = LocalDateTime.now();

            MessageEntity messageEntity1 = new MessageEntity();
            messageEntity1.setId(1L);
            messageEntity1.setUser(clientUser);
            messageEntity1.setChatroom(chatroomEntity);
            messageEntity1.setContent("I will not come tonight.");
            messageEntity1.setCreated(time);
            
            MessageEntity messageEntity2 = new MessageEntity();
            messageEntity2.setId(1L);
            messageEntity2.setUser(userEntity);
            messageEntity2.setChatroom(chatroomEntity);
            messageEntity2.setContent("Okay, see you tomorrow!");
            messageEntity2.setCreated(time);

            List<MessageEntity> messageEntities = new ArrayList<>();
            messageEntities.add(messageEntity1);
            messageEntities.add(messageEntity2);
            chatroomEntity.setMessages(messageEntities);

            List<ClientEntity> clientEntities = new ArrayList<>();
            clientEntities.add(clientEntity);
            coachEntity.setClients(clientEntities);
            
            MessageResponse messageResponse1 = new MessageResponse();
            messageResponse1.setId(1L);
            messageResponse1.setUsername("user456");
            messageResponse1.setContent("I will not come tonight.");
            messageResponse1.setCreated(time);
            messageResponse1.setChatroomId(1L); 
           
            MessageResponse messageResponse2 = new MessageResponse();
            messageResponse2.setId(1L);
            messageResponse2.setUsername("user123");
            messageResponse2.setContent("Okay, see you tomorrow!");
            messageResponse2.setCreated(time);
            messageResponse2.setChatroomId(1L); 
            
            List<MessageResponse> expectedResponse = new ArrayList<>();
            expectedResponse.add(messageResponse1);
            expectedResponse.add(messageResponse2);

            Mockito.when(chatroomRepository.findById(chatroomEntity.getId())).thenReturn(Optional.of(chatroomEntity));

            List<MessageResponse> actualResponse = chatroomService.getMessages(chatroomEntity.getId());

            Assertions.assertThat(actualResponse).hasSameElementsAs(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}
