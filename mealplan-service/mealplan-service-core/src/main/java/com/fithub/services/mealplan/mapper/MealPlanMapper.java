package com.fithub.services.mealplan.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;

@Mapper(componentModel = "spring")
public interface MealPlanMapper {

    @Mapping(source = "client.user.uuid", target = "client.uuid")
    @Mapping(source = "client.user.firstName", target = "client.firstName")
    @Mapping(source = "client.user.lastName", target = "client.lastName")
    @Mapping(source = "lastModifiedBy.user.uuid", target = "lastModifiedBy.uuid")
    @Mapping(source = "lastModifiedBy.user.firstName", target = "lastModifiedBy.firstName")
    @Mapping(source = "lastModifiedBy.user.lastName", target = "lastModifiedBy.lastName")
    public MealPlanResponse entityToDto(MealPlanEntity mealPlanEntity);

    public List<MealPlanResponse> entitiesToDtos(List<MealPlanEntity> mealPlanEntities);

}