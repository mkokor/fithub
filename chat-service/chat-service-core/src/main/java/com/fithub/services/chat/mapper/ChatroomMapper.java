package com.fithub.services.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.chat.dao.model.ChatroomEntity;
import com.fithub.services.chat.api.model.chatroom.ChatroomResponse;

@Mapper(componentModel = "spring")
public interface ChatroomMapper {

    @Mapping(source = "roomName", target = "roomName")
    @Mapping(source = "admin.id", target = "admin")
    public ChatroomResponse entityToDto(ChatroomEntity chatroomEntity);

}