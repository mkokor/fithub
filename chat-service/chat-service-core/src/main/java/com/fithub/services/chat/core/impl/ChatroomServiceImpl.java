package com.fithub.services.chat.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.api.exception.ApiException;
import com.fithub.services.chat.api.exception.NotFoundException;
import com.fithub.services.chat.api.model.chatroom.ChatroomDataResponse;
import com.fithub.services.chat.api.model.chatroom.ChatroomResponse;
import com.fithub.services.chat.api.model.user.UserResponse;
import com.fithub.services.chat.core.context.UserContext;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.ClientEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.dao.repository.UserRepository;
import com.fithub.services.chat.mapper.ChatroomMapper;
import com.fithub.services.chat.mapper.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatroomServiceImpl implements ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final ChatroomMapper chatroomMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    private List<UserResponse> getParticipants(Long chatroomId) throws ApiException {
        Optional<ChatroomEntity> chatroomEntity = chatroomRepository.findById(chatroomId);

        if (!chatroomEntity.isPresent()) {
            throw new NotFoundException("The chatroom with provided ID could not be found.");
        }

        CoachEntity coachEntity = chatroomEntity.get().getAdmin();

        List<ClientEntity> clientEntities = coachEntity.getClients();

        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(coachEntity.getUser());
        for (ClientEntity clientEntity : clientEntities) {
            UserEntity user = clientEntity.getUser();
            userEntities.add(user);
        }

        List<UserResponse> participants = userMapper.entitiesToDtos(userEntities);

        return participants;
    }

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
    public ChatroomDataResponse getChatroomData() throws ApiException {
        final CoachEntity coachEntity = extractCoachFromAuthenticatedUser();

        Optional<ChatroomEntity> chatroom = chatroomRepository.findByCoachId(coachEntity.getId());
        final ChatroomEntity chatroomEntity = chatroom.get();

        ChatroomResponse chatroomDetails = chatroomMapper.entityToDto(chatroomEntity);
        List<UserResponse> participants = getParticipants(chatroomEntity.getId());

        ChatroomDataResponse chatroomDataResponse = new ChatroomDataResponse();
        chatroomDataResponse.setChatroomDetails(chatroomDetails);
        chatroomDataResponse.setParticipants(participants);

        return chatroomDataResponse;
    }

    @Override
    public ChatroomResponse getChatroomDataByParticipiantUsername(final String username) throws ApiException {
        final Optional<UserEntity> user = userRepository.findByUsername(username);
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

        return chatroomMapper.entityToDto(chatroomEntity);
    }

}