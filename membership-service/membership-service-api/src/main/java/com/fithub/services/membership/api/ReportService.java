package com.fithub.services.membership.api;

import java.util.List;

import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.dao.model.MembershipEntity;

public interface ReportService {
	
	
	public List<PaymentRecordResponse> getReportOnMemberships() throws Exception;
}
