package com.fithub.services.training.api.membership;

import java.io.Serializable;

import lombok.Data;

@Data
public class MembershipPaymentReportResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private ClientMembershipResponse client;

    private Boolean hasDebt;

}