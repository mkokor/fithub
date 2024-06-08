package com.fithub.services.training.api;

import java.util.List;

import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.model.appointment.AppointmentResponse;
import com.fithub.services.training.api.model.reservation.ReservationResponse;

public interface AppointmentService {

    List<AppointmentResponse> getAppointments();

    List<AppointmentResponse> getAvailableAppointments() throws Exception;

    List<ReservationResponse> getReservations(Long appointmentId) throws Exception;

    ReservationResponse makeReservationForAppointment(Long appointmentId) throws ApiException;

}