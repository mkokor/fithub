package com.fithub.services.membership.api;

import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.model.membership.MembershipCreateRequest;
import com.fithub.services.membership.api.model.membership.MembershipPaymentReportResponse;
import com.fithub.services.membership.api.model.membership.MembershipResponse;

public interface MembershipService {

    MembershipPaymentReportResponse getMembershipPaymentReport(final String clientUuid) throws ApiException;

    MembershipResponse createClientMembership(final MembershipCreateRequest membershipCreateRequest) throws Exception;

}