package com.fithub.services.meal_plan.core.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fithub.services.meal_plan.api.CoachService;
import com.fithub.services.meal_plan.api.model.coach.CoachResponse;
import com.fithub.services.meal_plan.dao.repository.CoachRepository;
import com.fithub.services.meal_plan.mapper.CoachMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;

    @Override
    public List<CoachResponse> getAll() {
        return coachMapper.entitiesToDtos(coachRepository.findAll());
    }

}