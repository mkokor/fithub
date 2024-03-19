package com.fithub.services.chat.mapper;

import java.util.List;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.chat.dao.model.CoachEntity;
import com.fithub.services.chat.api.model.coach.CoachResponse;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    @Mapping(source = "user.username", target = "username")
    public CoachResponse entityToDto(CoachEntity coachEntity);

    public List<CoachResponse> entitiesToDtos(List<CoachEntity> coachEntities);

}