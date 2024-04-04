package com.fithub.services.membership.rest.membership;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.membership.api.MembershipService;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "membership", description = "Membership API")
@RestController
@RequestMapping(value = "membership")
@AllArgsConstructor
public class MembershipController {
	
	private final MembershipService membershipService;
	
	@Operation(summary = "Get payment record")
	@GetMapping(value = "/{id}/paymentrecord")
	public ResponseEntity<List<PaymentRecordResponse>> getPaymentRecord(@PathVariable Long id) throws Exception{
			return new ResponseEntity<>(membershipService.getPaymentRecord(id), HttpStatus.OK);
	}
	
	
	
	
 }
