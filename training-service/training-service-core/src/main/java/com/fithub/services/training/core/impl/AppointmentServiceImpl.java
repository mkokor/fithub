package com.fithub.services.training.core.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.exception.UnauthorizedException;
import com.fithub.services.training.api.model.appointment.AppointmentResponse;
import com.fithub.services.training.api.model.external.MembershipPaymentReportResponse;
import com.fithub.services.training.api.model.reservation.ReservationResponse;
import com.fithub.services.training.core.client.MembershipServiceClient;
import com.fithub.services.training.core.context.UserContext;
import com.fithub.services.training.dao.model.AppointmentEntity;
import com.fithub.services.training.dao.model.ClientEntity;
import com.fithub.services.training.dao.model.CoachEntity;
import com.fithub.services.training.dao.model.ReservationEntity;
import com.fithub.services.training.dao.model.UserEntity;
import com.fithub.services.training.dao.repository.AppointmentRepository;
import com.fithub.services.training.dao.repository.ReservationRepository;
import com.fithub.services.training.mapper.AppointmentMapper;
import com.fithub.services.training.mapper.ReservationMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final AppointmentMapper appointmentMapper;
    private final MembershipServiceClient membershipServiceClient;

    @Override
    public List<ReservationResponse> getReservations(Long appointmentId) throws Exception {
        final UserEntity userEntity = UserContext.getCurrentContext().getUser();
        final CoachEntity coachEntity = userEntity.getCoach();

        if (Objects.isNull(coachEntity)) {
            throw new UnauthorizedException("All reservations of the appointment can only be accessed by the coach.");
        }

        Optional<AppointmentEntity> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isEmpty()) {
            throw new NotFoundException("The appointment with provided ID could not be found.");
        }
        AppointmentEntity appointmentEntity = appointment.get();

        if (!appointmentEntity.getCoach().getUser().getUuid().equals(coachEntity.getUser().getUuid())) {
            throw new UnauthorizedException("The appointment is not related to the coach user.");
        }

        return reservationMapper.entitiesToDtos(appointmentEntity.getReservations());
    }

    @Override
    public List<AppointmentResponse> getAvailableAppointments() throws Exception {
        final UserEntity userEntity = UserContext.getCurrentContext().getUser();
        final ClientEntity clientEntity = userEntity.getClient();

        CoachEntity coachEntity;
        if (Objects.nonNull(clientEntity)) {
            coachEntity = clientEntity.getCoach();
        } else {
            coachEntity = userEntity.getCoach();
        }

        List<AppointmentEntity> availableAppointments = appointmentRepository.findAvailableAppointmentsByCoachId(coachEntity.getId());
        return appointmentMapper.entitiesToDtos(availableAppointments);
    }

    @Override
    public List<AppointmentResponse> getAppointments() {
        final UserEntity userEntity = UserContext.getCurrentContext().getUser();
        final ClientEntity clientEntity = userEntity.getClient();

        CoachEntity coachEntity;
        if (Objects.nonNull(clientEntity)) {
            coachEntity = clientEntity.getCoach();
        } else {
            coachEntity = userEntity.getCoach();
        }

        return appointmentMapper.entitiesToDtos(appointmentRepository.findByCoachId(coachEntity.getId()));
    }

    private Boolean isAppointmentAvailable(final AppointmentEntity appointmentEntity) {
        return appointmentEntity.getCapacity() > appointmentEntity.getReservations().size();
    }

    private Boolean clientHasReservation(final ClientEntity clientEntity, final AppointmentEntity appointmentEntity) {
        final List<ReservationEntity> reservations = clientEntity.getReservations();

        for (ReservationEntity reservation : reservations) {
            if (reservation.getAppointment().getId().equals(appointmentEntity.getId())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ReservationResponse makeReservationForAppointment(final Long appointmentId) throws ApiException {
        final UserEntity clientUser = UserContext.getCurrentContext().getUser();

        final ClientEntity clientEntity = clientUser.getClient();
        if (Objects.isNull(clientEntity)) {
            throw new UnauthorizedException("The reservation can be created only by the client.");
        }

        final CoachEntity coachEntity = clientEntity.getCoach();

        Optional<AppointmentEntity> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isEmpty()) {
            throw new NotFoundException("The appointment with provided ID could not be found.");
        }

        AppointmentEntity appointmentEntity = appointment.get();
        if (!appointmentEntity.getCoach().getId().equals(coachEntity.getId())) {
            throw new BadRequestException("The appointment with the provided ID is not related with client's coach.");
        }
        if (!isAppointmentAvailable(appointmentEntity)) {
            throw new BadRequestException("The appointment with the provided ID is not available.");
        }
        if (clientHasReservation(clientEntity, appointmentEntity)) {
            throw new BadRequestException("The requested reservation already exists.");
        }

        ResponseEntity<MembershipPaymentReportResponse> paymentReport = membershipServiceClient
                .getMembershipPaymentReport(coachEntity.getUser().getUuid(), clientUser.getUuid());
        if (paymentReport.getBody().getHasDebt()) {
            throw new BadRequestException("The client has unpayed debt.");
        }

        ReservationEntity reservation = new ReservationEntity();
        reservation.setAppointment(appointmentEntity);
        reservation.setClient(clientEntity);
        reservationRepository.save(reservation);

        return reservationMapper.entityToDto(reservation);

    }

}