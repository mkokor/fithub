package com.fithub.services.training.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.training.api.model.appointment.AppointmentResponse;
import com.fithub.services.training.dao.model.AppointmentEntity;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "coach.id", target = "coachId")
    public AppointmentResponse entityToDto(AppointmentEntity appointmentEntity);

    public List<AppointmentResponse> entitiesToDtos(List<AppointmentEntity> appointmentEntities);

}