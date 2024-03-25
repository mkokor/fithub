package com.fithub.services.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.auth.api.model.client.ClientResponse;
import com.fithub.services.auth.dao.model.ClientEntity;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "coach.id", target = "coachId")
    public ClientResponse entityToDto(ClientEntity clientEntity);

}