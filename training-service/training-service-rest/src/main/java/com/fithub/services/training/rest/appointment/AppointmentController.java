package com.fithub.services.training.rest.appointment;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.AppointmentService;
import com.fithub.services.training.api.model.appointment.AppointmentResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "appointment", description = "Appointment API")
@RestController
@RequestMapping(value = "appointment")
@AllArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(summary = "Get all appointments")
    @GetMapping
    public List<AppointmentResponse> getAll() {
        return appointmentService.getAll();
    }

}