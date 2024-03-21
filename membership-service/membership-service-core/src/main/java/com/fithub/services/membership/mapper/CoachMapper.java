package com.fithub.services.membership.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.membership.api.model.coach.CoachResponse;
import com.fithub.services.membership.dao.model.CoachEntity;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    public CoachResponse entityToDto(CoachEntity coachEntity);

    public List<CoachResponse> entitiesToDtos(List<CoachEntity> coachEntities);

}