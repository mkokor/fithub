package com.fithub.services.training.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.fithub.services.training.api.model.appointment.ClientAppointmentResponse;
import com.fithub.services.training.dao.model.AppointmentEntity;

@Mapper(componentModel = "spring")
public interface ClientAppointmentMapper {

    public List<ClientAppointmentResponse> entitiesToDtos(List<AppointmentEntity> appointmentEntities);

}