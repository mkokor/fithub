package com.fithub.services.training.api.model.client;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "The properties of a coach change request object")
public class CoachChangeRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The UUID of a new coach")
    @NotNull(message = "The UUID of a new coach must be specified.")
    private String coachUuid;

}