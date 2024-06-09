package com.fithub.services.chat.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.chat.api.model.message.MessageResponse;
import com.fithub.services.chat.dao.model.MessageEntity;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    @Mapping(source = "chatroom.id", target = "chatroomId")
    @Mapping(source = "user.username", target = "username")
    public MessageResponse entityToDto(MessageEntity messageEntity);

    public List<MessageResponse> entitiesToDtos(List<MessageEntity> messageEntities);

}