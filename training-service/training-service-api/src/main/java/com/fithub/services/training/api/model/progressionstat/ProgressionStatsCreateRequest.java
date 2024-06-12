package com.fithub.services.training.api.model.progressionstat;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProgressionStatsCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "The client UUID must be specified.")
    private String clientUuid;

    @NotNull(message = "The weigth must be specified.")
    private Double weight;

    @NotNull(message = "The height must be specified.")
    private Double height;

    @Min(value = 0, message = "The deadlift PR value must not be negative.")
    private Double deadliftPr;

    @Min(value = 0, message = "The squat PR value must not be negative.")
    private Double squatPr;

    @Min(value = 0, message = "The bench PR value must not be negative.")
    private Double benchPr;

    @Min(value = 0, message = "The treadmill PR value must not be negative.")
    private Double treadmillPr;

}