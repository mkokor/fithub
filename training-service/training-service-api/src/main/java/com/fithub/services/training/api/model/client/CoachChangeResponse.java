package com.fithub.services.training.api.model.client;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a coach change response object")
public class CoachChangeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientUuid;

    private String newCoachUuid;

}