package com.fithub.services.training.rest.appointment;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.model.appointment.CoachAppointmentResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "coach-appointment", description = "Coach Appointment API")
@RestController
@RequestMapping(value = "appointment")
@AllArgsConstructor
public class CoachAppointmentController {

    private final AppointmentService appointmentService;
    
    @Operation(summary = "Get appointments for coach")
    @GetMapping(value = "/coach/{id}")
    public ResponseEntity<List<CoachAppointmentResponse>> getAppointmentsForCoach(@Valid @PathVariable String id) throws Exception {
        return new ResponseEntity<>(appointmentService.getAppointmentsForCoach(id), HttpStatus.OK);
    }

}