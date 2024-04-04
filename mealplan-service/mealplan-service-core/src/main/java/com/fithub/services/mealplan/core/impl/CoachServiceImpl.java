package com.fithub.services.mealplan.core.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.mealplan.dao.model.CoachEntity;
import com.fithub.services.mealplan.dao.repository.CoachRepository;
import com.fithub.services.mealplan.dao.repository.UserRepository;
import com.fithub.services.mealplan.mapper.UserMapper;
import com.fithub.services.mealplan.api.CoachService;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.user.UserResponse;
import com.fithub.services.mealplan.dao.model.UserEntity;


import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CoachServiceImpl implements CoachService {
	
    private final CoachRepository coachRepository;
    private final UserMapper userMapper;


    @Override
    public UserResponse getCoachNameAndLastName(String userId) throws NotFoundException {
        Optional<CoachEntity> coachEntity = coachRepository.findByUserUuid(userId);

        if (coachEntity.isEmpty()) {
            throw new NotFoundException("The user associated with the coach ID could not be found");
        }
        
        return userMapper.entityToDto(coachEntity.get().getUser());

    }
}
