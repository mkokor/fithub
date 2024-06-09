package com.fithub.services.chat.test.suites;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertThrows;

import java.time.LocalDateTime;
import java.util.Optional;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fithub.services.chat.api.MessageService;
import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.api.model.message.MessageSendRequest;
import com.fithub.services.chat.core.impl.MessageServiceImpl;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.dao.repository.MessageRepository;
import com.fithub.services.chat.dao.repository.UserRepository;
import com.fithub.services.chat.mapper.MessageMapper;
import com.fithub.services.chat.test.configuration.BasicTestConfiguration;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

public class MessageServiceTest extends BasicTestConfiguration {

    @Autowired
    private MessageMapper messageMapper;

    private ChatroomRepository chatroomRepository;

    private MessageRepository messageRepository;

    private UserRepository userRepository;

    private MessageService messageService;

    private Validator validator;

    @BeforeMethod
    public void beforeMethod() {
        chatroomRepository = Mockito.mock(ChatroomRepository.class);
        messageRepository = Mockito.mock(MessageRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        validator = localValidatorFactoryBean;

        messageService = new MessageServiceImpl(chatroomRepository, messageRepository, userRepository, messageMapper, validator);
    }

    @Test
    public void testSendMessage_NoUserIdProvided_ThrowsMethodArgumentNotValidException() {
        try {
            MessageSendRequest messageSendRequest = new MessageSendRequest();
            messageSendRequest.setContent("this is my first message");

            assertThrows(ConstraintViolationException.class, () -> {
                messageService.sendMessage(messageSendRequest);
            });
        } catch (Exception exception) {
            Assert.fail();
        }
    }

    @Test
    public void testSendMessage_ValidDataProvided_ReturnsSentMessage() {
        try {
            UserEntity coachUser = new UserEntity();
            coachUser.setUsername("maryann");
            coachUser.setUuid("coach-id");

            CoachEntity coachEntity = new CoachEntity();
            coachEntity.setId(1L);
            coachEntity.setUser(coachUser);

            ChatroomEntity chatroomEntity = new ChatroomEntity();
            chatroomEntity.setId(1L);
            chatroomEntity.setAdmin(coachEntity);
            chatroomEntity.setRoomName("test-chatroom");

            LocalDateTime time = LocalDateTime.now();

            MessageSendRequest messageSendRequest = new MessageSendRequest();
            messageSendRequest.setContent("this is my first message");

            MessageResponse expectedResponse = new MessageResponse();
            expectedResponse.setUsername("maryann");
            expectedResponse.setContent("this is my first message");
            expectedResponse.setCreated(time);
            expectedResponse.setChatroomId(chatroomEntity.getId());

            Mockito.when(chatroomRepository.findById(chatroomEntity.getId())).thenReturn(Optional.of(chatroomEntity));
            Mockito.when(userRepository.findById(coachUser.getUuid())).thenReturn(Optional.of(coachUser));

            MessageResponse actualResponse = messageService.sendMessage(messageSendRequest);

            assertThat(actualResponse).usingRecursiveComparison().ignoringFields("id").isEqualTo(expectedResponse);
        } catch (Exception exception) {
            Assert.fail();
        }
    }

}