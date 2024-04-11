package com.fithub.services.training.api.model.membership;

import java.io.Serializable;

import com.fithub.services.training.api.model.client.ClientMembershipResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of the membership payment report.")
public class MembershipPaymentReportResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private ClientMembershipResponse client;

    private Boolean hasDebt;

}