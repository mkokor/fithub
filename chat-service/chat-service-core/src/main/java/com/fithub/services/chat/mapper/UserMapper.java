package com.fithub.services.chat.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.chat.dao.model.UserEntity;
import com.fithub.services.chat.api.model.user.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "username", target = "username")
    public UserResponse entityToDto(UserEntity userEntity);

    public List<UserResponse> entitiesToDtos(List<UserEntity> userEntities);

}