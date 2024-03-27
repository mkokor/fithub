package com.fithub.services.chat.core.impl;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.chat.api.UserService;
import com.fithub.services.chat.api.exception.NotFoundException;
import com.fithub.services.chat.api.model.user.UserResponse;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.ClientEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.mapper.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ChatroomRepository chatroomRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponse> getChatroomParticipants(Long chatroomId) throws Exception {
        Optional<ChatroomEntity> chatroomEntityOptional = chatroomRepository.findById(chatroomId);

        if (chatroomEntityOptional.isEmpty()) {
            throw new NotFoundException("The chatroom with provided ID could not be found.");
        }
        
        ChatroomEntity chatroomEntity = chatroomEntityOptional.get();
        CoachEntity coachEntity = chatroomEntity.getAdmin();
        List<ClientEntity> clientEntities = coachEntity.getClients();
        
        List<UserEntity> userEntities = new ArrayList<>();
        for (ClientEntity clientEntity : clientEntities) {
            UserEntity user = clientEntity.getUser();
            userEntities.add(user);
        }

        return userMapper.entitiesToDtos(userEntities);
    }
}
