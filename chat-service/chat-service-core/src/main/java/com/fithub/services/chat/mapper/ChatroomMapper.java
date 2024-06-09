package com.fithub.services.chat.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.chat.api.model.chatroom.ChatroomResponse;
import com.fithub.services.chat.dao.model.ChatroomEntity;

@Mapper(componentModel = "spring")
public interface ChatroomMapper {

    @Mapping(source = "roomName", target = "roomName")
    @Mapping(source = "admin.user.uuid", target = "admin.uuid")
    @Mapping(source = "admin.user.username", target = "admin.username")
    public ChatroomResponse entityToDto(ChatroomEntity chatroomEntity);

}