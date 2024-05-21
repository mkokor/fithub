package com.fithub.services.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.auth.api.rabbitmq.ClientRegistrationMessage;
import com.fithub.services.auth.dao.model.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "coach.user.uuid", target = "coachUuid")
    ClientRegistrationMessage entityToClientRegistrationMessage(UserEntity userEntity);

}