package com.fithub.services.chat.core.impl;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.api.exception.NotFoundException;
import com.fithub.services.chat.api.model.chatroom.ChatroomDataResponse;
import com.fithub.services.chat.api.model.chatroom.ChatroomResponse;
import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.api.model.user.UserResponse;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.ClientEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.mapper.ChatroomMapper;
import com.fithub.services.chat.mapper.MessageMapper;
import com.fithub.services.chat.mapper.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatroomServiceImpl implements ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final MessageMapper messageMapper;
    private final ChatroomMapper chatroomMapper;
    private final UserMapper userMapper;

    @Override
    public List<MessageResponse> getMessages(Long chatroomId) throws Exception {
        Optional<ChatroomEntity> chatroomEntity = chatroomRepository.findById(chatroomId);

        if (chatroomEntity.isEmpty()) {
            throw new NotFoundException("The chatroom with provided ID could not be found.");
        }

        return messageMapper.entitiesToDtos(chatroomEntity.get().getMessages());
    }
    
    @Override
    public ChatroomDataResponse getChatroomData(Long chatroomId) throws Exception {
    	Optional<ChatroomEntity> chatroomEntity = chatroomRepository.findById(chatroomId);
    	
        if (chatroomEntity.isEmpty()) {
            throw new NotFoundException("The chatroom with provided ID could not be found.");
        }
        
        ChatroomResponse chatroomDetails = chatroomMapper.entityToDto(chatroomEntity.get());
        
        List<MessageResponse> messages = messageMapper.entitiesToDtos(chatroomEntity.get().getMessages());
        
        CoachEntity coachEntity = chatroomEntity.get().getAdmin();
        
        List<ClientEntity> clientEntities = coachEntity.getClients();
        
        List<UserEntity> userEntities = new ArrayList<>();
        for (ClientEntity clientEntity : clientEntities) {
            UserEntity user = clientEntity.getUser();
            userEntities.add(user);
        }
        
        userEntities.add(coachEntity.getUser());
        
        List<UserResponse> participants = userMapper.entitiesToDtos(userEntities);
        
        ChatroomDataResponse chatroomDataResponse = new ChatroomDataResponse();
        chatroomDataResponse.setChatroomDetails(chatroomDetails);
        chatroomDataResponse.setMessages(messages);
        chatroomDataResponse.setParticipants(participants);
        
        return chatroomDataResponse;
    }

}