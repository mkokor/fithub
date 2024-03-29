package com.fithub.services.mealplan.core.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.mealplan.api.ClientService;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.repository.ClientRepository;
import com.fithub.services.mealplan.mapper.MealPlanMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService{

	private final ClientRepository clientRepository;
	private final MealPlanMapper mealPlanMapper;
	@Override
	public MealPlanResponse getMealPlan(Long clientId) throws Exception{
		Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);
		
		if (clientEntity.isEmpty()){
			throw new NotFoundException("The client with provided ID could not be found");
		}
		return mealPlanMapper.entityToDto(clientEntity.get().getMealPlan());
	}
	
	
}
