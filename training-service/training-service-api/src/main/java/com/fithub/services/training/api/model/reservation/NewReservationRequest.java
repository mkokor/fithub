package com.fithub.services.training.api.model.reservation;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "The properties of an reservation request object")
public class NewReservationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The appointment id")
    @NotNull(message = "The appointment id must not be blank.")
    private Long appointmentId;

}