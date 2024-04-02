package com.fithub.services.mealplan.core.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.mealplan.api.ClientService;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.api.model.user.UserResponse;
import com.fithub.services.mealplan.dao.model.ClientEntity;
import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.repository.ClientRepository;
import com.fithub.services.mealplan.dao.repository.CoachRepository;
import com.fithub.services.mealplan.mapper.MealPlanMapper;
import com.fithub.services.mealplan.mapper.UserMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService{

	private final ClientRepository clientRepository;
	private final MealPlanMapper mealPlanMapper;
	
    private final UserMapper userMapper;
	//private final ClientRepository clientRepository1;
	
	@Override
	public MealPlanResponse getMealPlan(Long clientId) throws Exception{
		Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);
		
		if (clientEntity.isEmpty()){
			throw new NotFoundException("The client with provided ID could not be found");
		}
		return mealPlanMapper.entityToDto(clientEntity.get().getMealPlan());
	}
	


    @Override
    public UserResponse getClientNameAndLastName(String userId) throws NotFoundException {
        Optional<ClientEntity> clientEntity = clientRepository.findByUserUuid(userId);

        if (clientEntity.isEmpty()) {
            throw new NotFoundException("The user associated with the client ID could not be found");
        }
        
        return userMapper.entityToDto(clientEntity.get().getUser());

    }
	
	
}
