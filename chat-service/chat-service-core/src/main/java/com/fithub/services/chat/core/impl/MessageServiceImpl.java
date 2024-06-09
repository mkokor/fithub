package com.fithub.services.chat.core.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fithub.services.chat.api.MessageService;
import com.fithub.services.chat.api.exception.NotFoundException;
import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.api.model.message.MessageSendRequest;
import com.fithub.services.chat.core.context.UserContext;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.ClientEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.MessageEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.dao.repository.MessageRepository;
import com.fithub.services.chat.dao.repository.UserRepository;
import com.fithub.services.chat.mapper.MessageMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ChatroomRepository chatroomRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final Validator validator;

    private CoachEntity extractCoachFromAuthenticatedUser() {
        final UserEntity userEntity = UserContext.getCurrentContext().getUser();
        final ClientEntity clientEntity = userEntity.getClient();

        CoachEntity coachEntity;
        if (Objects.nonNull(clientEntity)) {
            coachEntity = clientEntity.getCoach();
        } else {
            coachEntity = userEntity.getCoach();
        }

        return coachEntity;
    }

    @Override
    public MessageResponse sendMessage(MessageSendRequest messageSendRequest) throws Exception {
        Set<ConstraintViolation<MessageSendRequest>> violations = validator.validate(messageSendRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        final CoachEntity chatroomAdmin = extractCoachFromAuthenticatedUser();
        final Optional<ChatroomEntity> chatroom = chatroomRepository.findByCoachId(chatroomAdmin.getId());
        final UserEntity user = UserContext.getCurrentContext().getUser();

        final MessageEntity newMessage = new MessageEntity();
        newMessage.setContent(messageSendRequest.getContent());
        newMessage.setCreated(LocalDateTime.now());
        newMessage.setChatroom(chatroom.get());
        newMessage.setUser(user);

        messageRepository.save(newMessage);

        return messageMapper.entityToDto(newMessage);
    }

    @Override
    public MessageResponse sendMessageUnauthenticated(MessageSendRequest messageSendRequest) throws Exception {
        Set<ConstraintViolation<MessageSendRequest>> violations = validator.validate(messageSendRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        final Optional<UserEntity> user = userRepository.findByUsername(messageSendRequest.getUsername());
        if (user.isEmpty()) {
            throw new NotFoundException("The user with the provided username could not be found.");
        }

        final UserEntity userEntity = user.get();

        ChatroomEntity chatroomEntity;
        final ClientEntity clientEntity = userEntity.getClient();
        if (Objects.isNull(clientEntity)) {
            chatroomEntity = userEntity.getCoach().getChatroom();
        } else {
            chatroomEntity = clientEntity.getCoach().getChatroom();
        }

        final MessageEntity newMessage = new MessageEntity();
        newMessage.setContent(messageSendRequest.getContent());
        newMessage.setCreated(LocalDateTime.now());
        newMessage.setChatroom(chatroomEntity);
        newMessage.setUser(userEntity);

        messageRepository.save(newMessage);

        return messageMapper.entityToDto(newMessage);
    }

    @Override
    public List<MessageResponse> getMessages(Pageable pageable) {
        final CoachEntity coach = extractCoachFromAuthenticatedUser();
        final Optional<ChatroomEntity> chatroom = chatroomRepository.findByCoachId(coach.getId());

        final List<MessageEntity> messages = messageRepository.findByChatroomId(chatroom.get().getId(), pageable);
        return messageMapper.entitiesToDtos(messages);
    }

}