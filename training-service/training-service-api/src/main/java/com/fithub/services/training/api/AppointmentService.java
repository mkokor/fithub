package com.fithub.services.training.api;

import java.util.List;

import com.fithub.services.training.api.model.appointment.AppointmentResponse;
import com.fithub.services.training.api.model.appointment.ClientAppointmentResponse;
import com.fithub.services.training.api.model.appointment.CoachAppointmentResponse;
import com.fithub.services.training.api.model.reservation.NewReservationRequest;
import com.fithub.services.training.api.model.reservation.ReservationResponse;

public interface AppointmentService {

    List<ReservationResponse> getReservations(Long appointmentId) throws Exception;

    List<ClientAppointmentResponse> getAppointmentsForClient(String userId) throws Exception;

    List<CoachAppointmentResponse> getAppointmentsForCoach(String userId) throws Exception;

    List<AppointmentResponse> getAvailableAppointments(String userId) throws Exception;

    ReservationResponse makeReservationForAppointment(String userId, NewReservationRequest newReservationRequest) throws Exception;

}