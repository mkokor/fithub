package com.fithub.services.auth.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.auth.api.coach.CoachResponse;
import com.fithub.services.auth.dao.model.CoachEntity;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    CoachResponse entityToCoachResponse(CoachEntity coachEntity);

}