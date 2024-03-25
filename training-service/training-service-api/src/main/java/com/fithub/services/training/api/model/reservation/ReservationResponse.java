package com.fithub.services.training.api.model.reservation;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of an reservation response object")
public class ReservationResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long clientId;

    private Long appointmentId;

}