package com.fithub.services.training.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.fithub.services.training.api.model.progressionstat.ProgressionStatsCreateRequest;
import com.fithub.services.training.api.model.progressionstat.ProgressionStatsResponse;
import com.fithub.services.training.dao.model.ProgressionStatsEntity;

@Mapper(componentModel = "spring")
public interface ProgressionStatsMapper {

    ProgressionStatsEntity progressionStatCreateRequestToEntity(ProgressionStatsCreateRequest progressionStatCreateRequest);

    @Mapping(source = "client.user.uuid", target = "client.uuid")
    @Mapping(source = "client.user.firstName", target = "client.firstName")
    @Mapping(source = "client.user.lastName", target = "client.lastName")
    @Mapping(source = "createdBy.user.uuid", target = "coach.uuid")
    @Mapping(source = "createdBy.user.firstName", target = "coach.firstName")
    @Mapping(source = "createdBy.user.lastName", target = "coach.lastName")
    @Mapping(source = ".", target = "bmi", qualifiedByName = "calculateBmi")
    ProgressionStatsResponse entityToDto(ProgressionStatsEntity progressionStatsEntity);

    List<ProgressionStatsResponse> entitiesToDtos(List<ProgressionStatsEntity> progressionStatsEntities);

    @Named("calculateBmi")
    default double calculateBMmi(ProgressionStatsEntity entity) {
        double height = entity.getHeight();
        double weight = entity.getWeight();
        return height != 0 ? weight / (height * height) : 0;
    }

}