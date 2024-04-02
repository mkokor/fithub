package com.fithub.services.training.core.impl;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.model.appointment.ClientAppointmentResponse;
import com.fithub.services.training.api.model.reservation.ReservationResponse;
import com.fithub.services.training.dao.model.AppointmentEntity;
import com.fithub.services.training.dao.model.ClientEntity;
import com.fithub.services.training.dao.model.ReservationEntity;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.dao.repository.ClientRepository;
import com.fithub.services.training.mapper.ClientAppointmentMapper;
import com.fithub.services.training.mapper.ReservationMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ClientRepository clientRepository;
    private final ReservationMapper reservationMapper;
    private final ClientAppointmentMapper clientAppointmentMapper;

    @Override
    public List<ReservationResponse> getReservations(Long appointmentId) throws Exception {
        Optional<AppointmentEntity> appointmentEntity = appointmentRepository.findById(appointmentId);

        if (appointmentEntity.isEmpty()) {
            throw new NotFoundException("The appointment with provided ID could not be found.");
        }

        return reservationMapper.entitiesToDtos(appointmentEntity.get().getReservations());
    }
    
    @Override
    public List<ClientAppointmentResponse> getAppointmentsForClient(Long clientId) throws Exception {
        Optional<ClientEntity> clientEntity = clientRepository.findById(clientId);

        if (clientEntity.isEmpty()) {
            throw new NotFoundException("The client with provided ID could not be found.");
        }
        
        List<ReservationEntity> reservationEntities = clientEntity.get().getReservations();
        
        List<AppointmentEntity> appointmentEntities = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationEntities) {
            AppointmentEntity appointment = reservationEntity.getAppointment();
            appointmentEntities.add(appointment);
        }
        
        List<ClientAppointmentResponse> clientAppointments = clientAppointmentMapper.entitiesToDtos(appointmentEntities);
        return clientAppointments;
    }

}