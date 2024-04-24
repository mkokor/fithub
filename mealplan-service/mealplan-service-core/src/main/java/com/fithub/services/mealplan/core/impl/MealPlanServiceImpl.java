package com.fithub.services.mealplan.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.dao.model.MealPlanEntity;
import com.fithub.services.mealplan.dao.repository.MealPlanRepository;
import com.fithub.services.mealplan.mapper.DailyMealPlanMapper;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MealPlanServiceImpl implements MealPlanService{

	private final MealPlanRepository mealPlanRepository;
	private final DailyMealPlanMapper dailyMealPlanMapper;
	
	@Override
	public List<DailyMealPlanResponse> getDailyMealByDay(Long mealPlanId) throws Exception{
		Optional<MealPlanEntity> mealPlanEntity = mealPlanRepository.findById(mealPlanId);
		
		if (mealPlanEntity.isEmpty()){
			throw new NotFoundException("The client with provided ID could not be found");
		}
		return dailyMealPlanMapper.entitiesToDtos(mealPlanEntity.get().getMealPlans());
	}
	
	
		
}
