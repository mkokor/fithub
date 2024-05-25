package com.fithub.services.training.rest.appointment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.model.appointment.AppointmentResponse;
import com.fithub.services.training.api.model.reservation.ReservationResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "appointment", description = "Appointment API")
@RestController
@RequestMapping(value = "appointment", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Get reservations for appointment")
    @GetMapping(value = "/{id}/reservation")
    public ResponseEntity<List<ReservationResponse>> getReservations(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(appointmentService.getReservations(id), HttpStatus.OK);
    }

    @Operation(summary = "Get available appointments")
    @GetMapping(value = "/{id}")
    public ResponseEntity<List<AppointmentResponse>> getAvailableAppointments(@Valid @PathVariable String id) throws Exception {
        return new ResponseEntity<>(appointmentService.getAvailableAppointments(id), HttpStatus.OK);
    }

}