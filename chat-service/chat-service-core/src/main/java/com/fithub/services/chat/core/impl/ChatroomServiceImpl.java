package com.fithub.services.chat.core.impl;

import java.util.ArrayList;


import java.util.List;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.api.exception.BadRequestException;
import com.fithub.services.chat.api.exception.NotFoundException;
import com.fithub.services.chat.api.model.chatroom.ChatroomDataResponse;
import com.fithub.services.chat.api.model.chatroom.ChatroomResponse;
import com.fithub.services.chat.api.model.chatroom.NewChatroomRequest;
import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.api.model.user.UserResponse;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.model.ClientEntity;
import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.dao.repository.CoachRepository;
import com.fithub.services.chat.dao.repository.UserRepository;
import com.fithub.services.chat.mapper.ChatroomMapper;
import com.fithub.services.chat.mapper.MessageMapper;
import com.fithub.services.chat.mapper.UserMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatroomServiceImpl implements ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final CoachRepository coachRepository;
    private final UserRepository userRepository;
    private final MessageMapper messageMapper;
    private final ChatroomMapper chatroomMapper;
    private final UserMapper userMapper;
    private final Validator validator;
    
    private List<UserResponse> getParticipants(Long chatroomId) throws Exception {
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
    
    @Override
    public ChatroomDataResponse getChatroomData(String userId) throws Exception {
    	Optional<UserEntity> userEntity = userRepository.findById(userId);
        if (!userEntity.isPresent()) {
            throw new NotFoundException("The user with provided ID could not be found.");
        }
        
    	ClientEntity clientEntity = userEntity.get().getClient();
    	Long chatroomId = null;
    	if (clientEntity != null) {
    		CoachEntity coachEntity = clientEntity.getCoach();
    		chatroomId = coachEntity.getChatroom().getId();
    	} else {
    		CoachEntity coachEntity = userEntity.get().getCoach();
     		chatroomId = coachEntity.getChatroom().getId();
    	}
    	
    	Optional<ChatroomEntity> chatroomEntity = chatroomRepository.findById(chatroomId);
        if (!chatroomEntity.isPresent()) {
            throw new NotFoundException("The chatroom with provided ID could not be found.");
        }
        
        ChatroomResponse chatroomDetails = chatroomMapper.entityToDto(chatroomEntity.get());
        
        List<MessageResponse> messages = messageMapper.entitiesToDtos(chatroomEntity.get().getMessages());
        
        List<UserResponse> participants = getParticipants(chatroomId);
        
        ChatroomDataResponse chatroomDataResponse = new ChatroomDataResponse();
        chatroomDataResponse.setChatroomDetails(chatroomDetails);
        chatroomDataResponse.setMessages(messages);
        chatroomDataResponse.setParticipants(participants);
        
        return chatroomDataResponse;
    }
    
    @Override 
    public ChatroomDataResponse createNewChatroom(NewChatroomRequest newChatroomRequest) throws Exception {
	   Set<ConstraintViolation<NewChatroomRequest>> violations = validator.validate(newChatroomRequest);
       if (!violations.isEmpty()) {
           throw new ConstraintViolationException(violations);
       }
       
       Optional<CoachEntity> chatroomAdmin = coachRepository.findById(newChatroomRequest.getAdminId());
       if (!chatroomAdmin.isPresent()) {
           throw new NotFoundException("The coach with provided ID could not be found.");
       }
       
       if (chatroomAdmin.get().getChatroom() != null) {
    	   throw new BadRequestException("The chatroom for given admin already exists.");
       }
       
       final ChatroomEntity newChatroom = new ChatroomEntity();
       newChatroom.setRoomName(newChatroomRequest.getRoomName());
       newChatroom.setAdmin(chatroomAdmin.get());
       chatroomRepository.save(newChatroom);
       
       ChatroomDataResponse chatroomDataResponse = getChatroomData(chatroomAdmin.get().getUser().getUuid());
       
       return chatroomDataResponse;
    }
}