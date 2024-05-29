package com.fithub.services.mealplan.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.mealplan.api.model.client.ClientResponse;
import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.dao.model.ClientEntity;


@Mapper(componentModel = "spring")
public interface ClientMapper {
	
	@Mapping(source = "user.uuid", target = "userId")
    @Mapping(source = "coach.id", target = "coachId")
	
	@Mapping(source = "user.firstName", target = "firstName")
	@Mapping(source = "user.lastName", target = "lastName")
    public ClientResponse entityToDto(ClientEntity clientEntity);
	
	 public List<ClientResponse> entitiesToDtos(List<ClientEntity> clientEntities);



}