package com.fithub.services.mealplan.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;

@Mapper(componentModel = "spring")
public interface MealPlanMapper {
    
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "modifiedBy.id", target = "modifiedBy")
    public MealPlanResponse entityToDto(MealPlanEntity mealPlanEntity);

	public List<MealPlanResponse> entitiesToDtos (List<MealPlanEntity> mealPlanEntities);

}