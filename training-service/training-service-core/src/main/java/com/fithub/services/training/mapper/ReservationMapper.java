package com.fithub.services.training.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.training.api.model.reservation.ReservationResponse;
import com.fithub.services.training.dao.model.ReservationEntity;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "appointment.id", target = "appointmentId")
    public ReservationResponse entityToDto(ReservationEntity reservationEntity);

    public List<ReservationResponse> entitiesToDtos(List<ReservationEntity> appointmentEntities);

}