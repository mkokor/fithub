package com.fithub.services.chat.core.impl;

import java.util.List;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.chat.api.ChatroomService;
import com.fithub.services.chat.api.exception.NotFoundException;
import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.dao.repository.ChatroomRepository;
import com.fithub.services.chat.mapper.MessageMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChatroomServiceImpl implements ChatroomService {

    private final ChatroomRepository chatroomRepository;
    private final MessageMapper messageMapper;

    @Override
    public List<MessageResponse> getMessages(Long chatroomId) throws Exception {
        Optional<ChatroomEntity> chatroomEntity = chatroomRepository.findById(chatroomId);

        if (chatroomEntity.isEmpty()) {
            throw new NotFoundException("The chatroom with provided ID could not be found.");
        }

        return messageMapper.entitiesToDtos(chatroomEntity.get().getMessages());
    }

}