package com.fithub.services.membership.rest.membership;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.membership.api.MembershipService;
import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.model.membership.MembershipCreateRequest;
import com.fithub.services.membership.api.model.membership.MembershipPaymentReportResponse;
import com.fithub.services.membership.api.model.membership.MembershipResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "membership", description = "Membership API")
@RestController
@RequestMapping(value = "membership", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @Operation(summary = "Get membership payment report of a client")
    @GetMapping("/report")
    public ResponseEntity<MembershipPaymentReportResponse> getMembershipPaymentReport(@RequestParam("client_uuid") String clientUuid)
            throws ApiException {
        return new ResponseEntity<>(membershipService.getMembershipPaymentReport(clientUuid), HttpStatus.OK);
    }

    @Operation(summary = "Create membership for a client")
    @PostMapping
    public ResponseEntity<MembershipResponse> createClientMembership(
            @RequestBody @Valid final MembershipCreateRequest membershipCreateRequest) throws Exception {
        return new ResponseEntity<>(membershipService.createClientMembership(membershipCreateRequest), HttpStatus.OK);
    }

}