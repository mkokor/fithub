package com.fithub.services.membership.api;

import java.util.List;

import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.model.membership.MembershipPaymentReportResponse;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;

public interface MembershipService {

    List<PaymentRecordResponse> getPaymentRecord(Long membershipId) throws Exception;

    MembershipPaymentReportResponse getMembershipPaymentReport(String clientUuid) throws ApiException;

}