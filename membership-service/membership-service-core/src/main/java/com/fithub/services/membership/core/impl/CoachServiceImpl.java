package com.fithub.services.membership.core.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fithub.services.membership.api.CoachService;
import com.fithub.services.membership.api.model.coach.CoachResponse;
import com.fithub.services.membership.dao.repository.CoachRepository;
import com.fithub.services.membership.mapper.CoachMapper;

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