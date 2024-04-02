package com.fithub.services.training.rest.appointment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.model.appointment.ClientAppointmentResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "client-appointment", description = "Client Appointment API")
@RestController
@RequestMapping(value = "appointment")
@AllArgsConstructor
public class ClientAppointmentController {

    private final AppointmentService appointmentService;
    
    @Operation(summary = "Get appointments for client")
    @GetMapping(value = "/client-appointments/{id}")
    public ResponseEntity<List<ClientAppointmentResponse>> getAppointmentsForClient(@Valid @PathVariable String id) throws Exception {
        return new ResponseEntity<>(appointmentService.getAppointmentsForClient(id), HttpStatus.OK);
    }

}