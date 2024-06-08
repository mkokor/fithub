package com.fithub.services.membership.api.model.membership;

import java.io.Serializable;

import com.fithub.services.membership.api.model.client.ClientResponse;
import com.fithub.services.membership.api.model.coach.CoachResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a membership response object.")
public class MembershipResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Double monthlyAmount;
    private ClientResponse client;
    private CoachResponse coach;

}