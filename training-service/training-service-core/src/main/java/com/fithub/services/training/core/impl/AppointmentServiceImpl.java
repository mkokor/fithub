package com.fithub.services.training.core.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.exception.BadRequestException;
import com.fithub.services.training.api.exception.NotFoundException;
import com.fithub.services.training.api.exception.UnauthorizedException;
import com.fithub.services.training.api.model.appointment.AppointmentResponse;
import com.fithub.services.training.api.model.appointment.ClientAppointmentResponse;
import com.fithub.services.training.api.model.appointment.CoachAppointmentResponse;
import com.fithub.services.training.api.model.external.MembershipPaymentReportResponse;
import com.fithub.services.training.api.model.reservation.NewReservationRequest;
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
import com.fithub.services.training.dao.repository.UserRepository;
import com.fithub.services.training.mapper.AppointmentMapper;
import com.fithub.services.training.mapper.ClientAppointmentMapper;
import com.fithub.services.training.mapper.CoachAppointmentMapper;
import com.fithub.services.training.mapper.ReservationMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final AppointmentMapper appointmentMapper;
    private final ClientAppointmentMapper clientAppointmentMapper;
    private final CoachAppointmentMapper coachAppointmentMapper;
    private final Validator validator;
    private final MembershipServiceClient membershipServiceClient;

    @Override
    public List<ReservationResponse> getReservations(Long appointmentId) throws Exception {
        final UserEntity userEntity = UserContext.getCurrentContext().getUser();
        final CoachEntity coachEntity = userEntity.getCoach();

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
    public List<AppointmentResponse> getAvailableAppointments(String userId) throws Exception {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (!userEntity.isPresent()) {
            throw new NotFoundException("The user with provided ID could not be found.");
        }

        ClientEntity client = userEntity.get().getClient();
        CoachEntity coach = null;
        if (client != null) {
            coach = client.getCoach();
        } else {
            coach = userEntity.get().getCoach();
        }

        List<AppointmentEntity> availableAppointments = appointmentRepository.findAvailableAppointmentsByCoachId(coach.getId());
        List<AppointmentResponse> response = appointmentMapper.entitiesToDtos(availableAppointments);
        return response;
    }

    @Override
    public List<ClientAppointmentResponse> getAppointmentsForClient(String userId) throws Exception {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isEmpty()) {
            throw new NotFoundException("The user with provided ID could not be found.");
        }

        ClientEntity clientEntity = userEntity.get().getClient();

        if (clientEntity == null) {
            throw new BadRequestException("The provider user ID is not valid.");
        }

        List<ReservationEntity> reservationEntities = clientEntity.getReservations();

        List<AppointmentEntity> appointmentEntities = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationEntities) {
            AppointmentEntity appointment = reservationEntity.getAppointment();
            appointmentEntities.add(appointment);
        }

        List<ClientAppointmentResponse> clientAppointments = clientAppointmentMapper.entitiesToDtos(appointmentEntities);
        return clientAppointments;
    }

    @Override
    public List<CoachAppointmentResponse> getAppointmentsForCoach(String userId) throws Exception {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isEmpty()) {
            throw new NotFoundException("The user with provided ID could not be found.");
        }

        CoachEntity coachEntity = userEntity.get().getCoach();

        if (coachEntity == null) {
            throw new BadRequestException("The provider user ID is not valid.");
        }

        List<AppointmentEntity> appointmentEntities = coachEntity.getAppointments();

        List<CoachAppointmentResponse> coachAppointments = coachAppointmentMapper.entitiesToDtos(appointmentEntities);
        return coachAppointments;
    }

    @Override
    public ReservationResponse makeReservationForAppointment(String userId, NewReservationRequest newReservationRequest) throws Exception {
        Set<ConstraintViolation<NewReservationRequest>> violations = validator.validate(newReservationRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (!userEntity.isPresent()) {
            throw new NotFoundException("The user with provided ID could not be found.");
        }

        if (userEntity.get().getClient() == null) {
            throw new BadRequestException("Only client users can make reservations.");
        }

        Optional<AppointmentEntity> appointment = appointmentRepository.findById(newReservationRequest.getAppointmentId());

        if (appointment.isEmpty()) {
            throw new NotFoundException("The appointment with provided ID could not be found.");
        }

        ResponseEntity<MembershipPaymentReportResponse> paymentReport = membershipServiceClient.getMembershipPaymentReport(userId);
        if (paymentReport.getBody().getHasDebt()) {
            throw new BadRequestException("The user has unpayed debt.");
        }

        List<AppointmentResponse> availableAppointments = getAvailableAppointments(userId);
        AppointmentResponse appointmentResponse = appointmentMapper.entityToDto(appointment.get());
        if (availableAppointments.contains(appointmentResponse)) {

            ReservationEntity existingReservation = reservationRepository.findReservationByClientId(appointment.get().getId(),
                    userEntity.get().getClient().getId());
            if (existingReservation != null) {
                throw new BadRequestException("Client already has a reservation for this appointment.");
            }

            ReservationEntity reservation = new ReservationEntity();
            reservation.setAppointment(appointment.get());
            reservation.setClient(userEntity.get().getClient());
            reservationRepository.save(reservation);
            return reservationMapper.entityToDto(reservation);

        } else {
            throw new BadRequestException("Appointment with provided id is not available.");
        }
    }

}