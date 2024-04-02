package com.fithub.services.training.api.model.appointment;

import java.io.Serializable;
import java.time.LocalTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a client appointment response object")
public class ClientAppointmentResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private LocalTime startTime;

    private LocalTime endTime;

    private String day;

}