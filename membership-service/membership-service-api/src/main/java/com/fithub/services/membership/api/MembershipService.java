package com.fithub.services.membership.api;

import java.util.List;

import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;

public interface MembershipService {
	
	
	List<PaymentRecordResponse> getPaymentRecord(Long membershipId) throws Exception;
	
	

}
