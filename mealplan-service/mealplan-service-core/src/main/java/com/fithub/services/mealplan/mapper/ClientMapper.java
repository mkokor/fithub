package com.fithub.services.mealplan.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.mealplan.api.model.client.ClientResponse;
import com.fithub.services.mealplan.dao.model.ClientEntity;

@Mapper(componentModel = "spring")
public interface ClientMapper {
	
	@Mapping(source = "user.uuid", target = "userId")
    @Mapping(source = "coach.id", target = "coachId")
    public ClientResponse entityToDto(ClientEntity clientEntity);



}