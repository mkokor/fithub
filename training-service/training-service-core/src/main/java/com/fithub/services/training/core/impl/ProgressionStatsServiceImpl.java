package com.fithub.services.training.core.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.ProgressionStatsService;
import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.exception.UnauthorizedException;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsCreateRequest;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsResponse;
import com.fithub.services.training.core.context.UserContext;
import com.fithub.services.training.dao.model.ClientEntity;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.ProgressionStatsEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.ProgressionStatsRepository;
import com.fithub.services.training.dao.repository.UserRepository;
import com.fithub.services.training.mapper.ProgressionStatsMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgressionStatsServiceImpl implements ProgressionStatsService {

    private final ProgressionStatsRepository progressionStatsRepository;
    private final UserRepository userRepository;
    private final ProgressionStatsMapper progressionStatsMapper;
    private final Validator validator;

    private UserEntity getAuthenticatedUser() {
        return UserContext.getCurrentContext().getUser();
    }

    private ClientEntity validateClientCoachRelationship(final String clientUuid) throws ApiException {
        final Optional<UserEntity> clientUser = userRepository.findById(clientUuid);
        if (clientUser.isEmpty()) {
            throw new NotFoundException("The client with the provided UUID could not be found.");
        }

        if (Objects.nonNull(clientUser.get().getCoach())) {
            throw new BadRequestException("The provided UUID is associated with coach user.");
        }

        final ClientEntity clientEntity = clientUser.get().getClient();
        final UserEntity authenticatedUser = getAuthenticatedUser();
        if (!clientEntity.getCoach().getUser().getUuid().equals(authenticatedUser.getUuid())) {
            throw new UnauthorizedException("The requested client and the authenticated coach are not associated.");
        }

        return clientEntity;
    }

    @Override
    public ProgressionStatsResponse create(ProgressionStatsCreateRequest progressionStatCreateRequest) throws Exception {
        Set<ConstraintViolation<ProgressionStatsCreateRequest>> violations = validator.validate(progressionStatCreateRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        final CoachEntity authenticatedCoach = getAuthenticatedUser().getCoach();
        final ClientEntity clientEntity = validateClientCoachRelationship(progressionStatCreateRequest.getClientUuid());

        ProgressionStatsEntity progressionStatsEntity = progressionStatsMapper
                .progressionStatCreateRequestToEntity(progressionStatCreateRequest);
        progressionStatsEntity.setClient(clientEntity);
        progressionStatsEntity.setCreatedAt(LocalDateTime.now());
        progressionStatsEntity.setCreatedBy(authenticatedCoach);

        progressionStatsRepository.save(progressionStatsEntity);

        return progressionStatsMapper.entityToDto(progressionStatsEntity);
    }

    @Override
    public List<ProgressionStatsResponse> getProgressionStats(String clientUuid) throws ApiException {
        validateClientCoachRelationship(clientUuid);

        final List<ProgressionStatsEntity> progressionStatsEntities = progressionStatsRepository.findByClientUuid(clientUuid);
        return progressionStatsMapper.entitiesToDtos(progressionStatsEntities);
    }

    @Override
    public List<ProgressionStatsResponse> getRangList(Pageable pageable) throws ApiException {
        List<ProgressionStatsEntity> progressionStatsEntities = progressionStatsRepository.findByDistinctClientIds(pageable);
        return progressionStatsMapper.entitiesToDtos(progressionStatsEntities);
    }

    @Override
    public ProgressionStatsResponse getLatestStats() throws ApiException {
        final UserEntity clientUser = getAuthenticatedUser();

        List<ProgressionStatsEntity> progressionStatsEntity = progressionStatsRepository.findLatestByClientUuid(clientUser.getUuid());

        if (progressionStatsEntity.isEmpty()) {
            throw new NotFoundException("The client does not have any progression stats yet.");
        }

        return progressionStatsMapper.entityToDto(progressionStatsEntity.get(0));
    }

}