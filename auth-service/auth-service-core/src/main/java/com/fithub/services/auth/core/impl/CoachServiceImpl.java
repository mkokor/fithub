package com.fithub.services.auth.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.auth.api.CoachService;
import com.fithub.services.auth.api.coach.CoachLoV;
import com.fithub.services.auth.api.coach.CoachResponse;
import com.fithub.services.auth.api.exception.NotFoundException;
import com.fithub.services.auth.dao.model.CoachEntity;
import com.fithub.services.auth.dao.repository.CoachRepository;
import com.fithub.services.auth.mapper.CoachMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final CoachMapper coachMapper;

    @Override
    public List<CoachLoV> getAvailableCoaches() {
        return coachRepository.findAvailableCoaches();
    }

    @Override
    public CoachResponse getCoachById(Long id) throws NotFoundException {
        Optional<CoachEntity> coachEntity = coachRepository.findById(id);
        if (coachEntity.isEmpty()) {
            throw new NotFoundException("The coach with the provided ID could not be found.");
        }

        return coachMapper.entityToCoachResponse(coachEntity.get());
    }

}