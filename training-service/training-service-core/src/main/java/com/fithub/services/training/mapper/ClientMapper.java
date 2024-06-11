package com.fithub.services.training.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.training.api.model.client.ClientResponse;
import com.fithub.services.training.dao.model.ClientEntity;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "user.uuid", target = "uuid")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    ClientResponse entityToDto(ClientEntity coachEntity);

    List<ClientResponse> entitiesToDtos(List<ClientEntity> coachEntities);

}