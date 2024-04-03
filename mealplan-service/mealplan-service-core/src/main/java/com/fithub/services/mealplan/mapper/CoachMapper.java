package com.fithub.services.mealplan.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.dao.model.CoachEntity;

@Mapper(componentModel = "spring")
public interface CoachMapper {

    @Mapping(source = "user.uuid", target = "userId")
    public CoachResponse entityToDto(CoachEntity coachEntity);

    public List<CoachResponse> entitiesToDtos(List<CoachEntity> coachEntities);

}