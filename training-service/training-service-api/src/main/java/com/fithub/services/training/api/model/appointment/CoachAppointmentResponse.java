package com.fithub.services.training.api.model.appointment;

import java.io.Serializable;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a coach appointment response object")
public class CoachAppointmentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer capacity;

    private String day;
    
}