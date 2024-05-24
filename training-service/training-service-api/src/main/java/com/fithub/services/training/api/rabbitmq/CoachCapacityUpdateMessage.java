package com.fithub.services.training.api.rabbitmq;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CoachCapacityUpdateMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String updateEventType;

    private String coachUuid;
    private Integer oldCapacityValue;
    private Integer newCapacityValue;

}