package com.fithub.services.chat.core.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class MessageServiceImpl implements MessageService {

    private final ChatroomRepository chatroomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final Validator validator;

    @Autowired
    public MessageServiceImpl(ChatroomRepository chatroomRepository, MessageRepository messageRepository, UserRepository userRepository,
            MessageMapper messageMapper, Validator validator) {
        this.chatroomRepository = chatroomRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
        this.validator = validator;
    }

    @Override
    public MessageResponse sendMessage(MessageSendRequest messageSendRequest, Long chatroomId) throws Exception {
        final Optional<ChatroomEntity> chatroom = chatroomRepository.findById(chatroomId);
        final Optional<UserEntity> user = userRepository.findById(messageSendRequest.getUserId());
        Set<ConstraintViolation<MessageSendRequest>> violations = validator.validate(messageSendRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        if (!chatroom.isPresent()) {
            throw new NotFoundException("The chatroom with provided ID could not be found.");
        }

        if (!user.isPresent()) {
            throw new NotFoundException("The user with provided ID could not be found.");
        }

        final MessageEntity newMessage = new MessageEntity();
        newMessage.setContent(messageSendRequest.getContent());
        newMessage.setCreated(messageSendRequest.getCreated());
        newMessage.setChatroom(chatroom.get());
        newMessage.setUser(user.get());

        messageRepository.save(newMessage);

        return messageMapper.entityToDto(newMessage);
    }

}