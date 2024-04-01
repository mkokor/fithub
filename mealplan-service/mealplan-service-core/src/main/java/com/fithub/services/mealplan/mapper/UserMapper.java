package com.fithub.services.mealplan.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.api.model.user.UserResponse;
import com.fithub.services.mealplan.dao.model.UserEntity;


@Mapper(componentModel = "spring")
public interface UserMapper {
    public UserResponse entityToDto(UserEntity userEntity); 
}