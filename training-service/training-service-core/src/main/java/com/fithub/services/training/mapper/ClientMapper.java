package com.fithub.services.training.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.training.api.model.client.CoachChangeResponse;
import com.fithub.services.training.dao.model.ClientEntity;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "clientEntity.user.uuid", target = "clientUuid")
    @Mapping(source = "clientEntity.coach.user.uuid", target = "newCoachUuid")
    CoachChangeResponse clientEntityToCoachChangeResponse(ClientEntity clientEntity);

}