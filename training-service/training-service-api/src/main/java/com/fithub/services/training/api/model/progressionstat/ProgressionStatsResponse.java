package com.fithub.services.training.api.model.progressionstat;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fithub.services.training.api.model.client.ClientResponse;
import com.fithub.services.training.api.model.coach.CoachResponse;

import lombok.Data;

@Data
public class ProgressionStatsResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private ClientResponse client;
    private CoachResponse coach;
    private Double deadliftPr;
    private Double squatPr;
    private Double benchPr;
    private Double treadmillPr;
    private LocalDateTime createdAt;
    private Double bmi;

}