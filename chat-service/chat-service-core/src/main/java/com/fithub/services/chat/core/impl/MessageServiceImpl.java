package com.fithub.services.chat.core.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fithub.services.chat.api.MessageService;
import com.fithub.services.chat.api.exception.NotFoundException;
import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.api.model.message.MessageSendRequest;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.MessageEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.dao.repository.MessageRepository;
import com.fithub.services.chat.dao.repository.UserRepository;
import com.fithub.services.chat.mapper.MessageMapper;
import com.fithub.services.systemevents.ActionLogRequest;
import com.fithub.services.systemevents.ActionLoggerServiceGrpc;
import com.fithub.services.systemevents.VoidResponse;
import com.fithub.services.chat.api.enums.ActionType;
import com.fithub.services.chat.api.enums.ResourceType;
import com.fithub.services.chat.api.enums.ResponseType;

import io.grpc.stub.StreamObserver;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import net.devh.boot.grpc.client.inject.GrpcClient;

@Service
public class MessageServiceImpl implements MessageService {

    private final ChatroomRepository chatroomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final Validator validator;
    
    @Value("${spring.application.name}")
    private String microserviceName;

    @GrpcClient("system-events")
    private ActionLoggerServiceGrpc.ActionLoggerServiceStub systemEventsClient;
    
    @Autowired
    public MessageServiceImpl(ChatroomRepository chatroomRepository, MessageRepository messageRepository, UserRepository userRepository, MessageMapper messageMapper, Validator validator) {
    	this.chatroomRepository = chatroomRepository;
    	this.messageRepository = messageRepository;
    	this.userRepository = userRepository;
    	this.messageMapper = messageMapper;
    	this.validator = validator;
    }
    
    private void sendActionLogRequest(ActionType actionType, ResponseType responseType) {
        ActionLogRequest actionLogRequest = ActionLogRequest.newBuilder().setMicroserviceName(microserviceName)
                .setActionType(actionType.toString()).setResourceTitle(ResourceType.CHAT_MESSAGE.toString())
                .setResponseType(responseType.toString()).build();

        systemEventsClient.logAction(actionLogRequest, new StreamObserver<VoidResponse>() {

            @Override
            public void onNext(VoidResponse response) {
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
            }

        });
    }

    @Override
    public MessageResponse sendMessage(MessageSendRequest messageSendRequest, Long chatroomId) throws Exception {
        final Optional<ChatroomEntity> chatroom = chatroomRepository.findById(chatroomId);
        final Optional<UserEntity> user = userRepository.findById(messageSendRequest.getUserId());
        Set<ConstraintViolation<MessageSendRequest>> violations = validator.validate(messageSendRequest);
        if (!violations.isEmpty()) {
        	sendActionLogRequest(ActionType.CREATE, ResponseType.ERROR);
            throw new ConstraintViolationException(violations);
        }

        if (!chatroom.isPresent()) {
        	sendActionLogRequest(ActionType.CREATE, ResponseType.ERROR);
            throw new NotFoundException("The chatroom with provided ID could not be found.");
        }
        
        if(!user.isPresent()) {
        	sendActionLogRequest(ActionType.CREATE, ResponseType.ERROR);
            throw new NotFoundException("The user with provided ID could not be found.");
        }

        final MessageEntity newMessage = new MessageEntity();
        newMessage.setContent(messageSendRequest.getContent());
        newMessage.setCreated(messageSendRequest.getCreated());
        newMessage.setChatroom(chatroom.get());
        newMessage.setUser(user.get());

        messageRepository.save(newMessage);

    	sendActionLogRequest(ActionType.CREATE, ResponseType.SUCCESS);
        return messageMapper.entityToDto(newMessage);
    }

}