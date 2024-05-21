package com.fithub.services.mealplan.mapper;


import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.dao.model.DailyMealPlanEntity;

@Mapper(componentModel = "spring")
public interface DailyMealPlanMapper {
    
    //@Mapping(source = "mealPlan.id", target = "mealPlanId")
	public List<DailyMealPlanResponse> entitiesToDtos (List<DailyMealPlanEntity> dailyMealPlanEntities);
	
	public DailyMealPlanResponse entityToDto (DailyMealPlanEntity dailyMealPlanEntity);

}