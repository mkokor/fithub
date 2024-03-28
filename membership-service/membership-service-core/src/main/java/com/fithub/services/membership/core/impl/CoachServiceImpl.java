package com.fithub.services.membership.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.membership.api.CoachService;
import com.fithub.services.membership.api.exception.NotFoundException;
import com.fithub.services.membership.api.model.client.ClientResponse;
import com.fithub.services.membership.dao.model.CoachEntity;
import com.fithub.services.membership.dao.repository.CoachRepository;
import com.fithub.services.membership.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CoachServiceImpl implements CoachService {

    private final CoachRepository coachRepository;
    private final ClientMapper clientMapper;

    @Override
	public List<ClientResponse> getClients(Long coachId) throws Exception {
		
		Optional<CoachEntity> coachEntity = coachRepository.findById(coachId);
		
		if (coachEntity.isEmpty()) {
			throw new NotFoundException("The coach with the provided ID could not be found");
		}
		
		return clientMapper.entitiesToDtos(coachEntity.get().getClients());
    }

}