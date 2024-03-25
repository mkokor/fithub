package com.fithub.services.training.api;

import java.util.List;

import com.fithub.services.training.api.model.reservation.ReservationResponse;

public interface AppointmentService {

    List<ReservationResponse> getReservations(Long appointmentId) throws Exception;

}