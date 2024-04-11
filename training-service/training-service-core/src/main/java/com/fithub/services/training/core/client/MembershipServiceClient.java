package com.fithub.services.training.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fithub.services.training.api.model.membership.MembershipPaymentReportResponse;

@FeignClient(name = "fithub-membership-service")
public interface MembershipServiceClient {

    @GetMapping("/membership/report")
    ResponseEntity<MembershipPaymentReportResponse> getMembershipPaymentReport(@RequestParam("client_uuid") String clientUuid);
  
}