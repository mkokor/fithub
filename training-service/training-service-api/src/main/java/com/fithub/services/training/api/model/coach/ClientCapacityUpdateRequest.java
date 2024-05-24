package com.fithub.services.training.api.model.coach;

import java.io.Serializable;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ClientCapacityUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Min(value = 1, message = "The client capacity must be at least 1.")
    @Max(value = 25, message = "The client capacity must be up to 25.")
    @NotNull(message = "The client capacity must be provided.")
    private Integer newClientCapacity;

}