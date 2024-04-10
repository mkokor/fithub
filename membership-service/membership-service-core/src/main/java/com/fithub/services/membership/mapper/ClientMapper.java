package com.fithub.services.membership.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.membership.api.model.client.ClientResponse;
import com.fithub.services.membership.dao.model.ClientEntity;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "coach.id", target = "coachId")
    public ClientResponse entityToDto(ClientEntity clientEntity);

    public List<ClientResponse> entitiesToDtos(List<ClientEntity> clientEntities);

}