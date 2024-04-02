package com.fithub.services.training.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.model.reservation.ReservationResponse;
import com.fithub.services.training.core.client.AuthServiceClient;
import com.fithub.services.training.dao.model.AppointmentEntity;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.mapper.ReservationMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ReservationMapper reservationMapper;
    private final AuthServiceClient authServiceClient;

    @Override
    public List<ReservationResponse> getReservations(Long appointmentId) throws Exception {
        Optional<AppointmentEntity> appointmentEntity = appointmentRepository.findById(appointmentId);

        if (appointmentEntity.isEmpty()) {
            throw new NotFoundException("The appointment with provided ID could not be found.");
        }

        return reservationMapper.entitiesToDtos(appointmentEntity.get().getReservations());
    }

    @Override
    public String testLoadBalancer() {
        return authServiceClient.testLoadBalancing().getBody();
    }

}