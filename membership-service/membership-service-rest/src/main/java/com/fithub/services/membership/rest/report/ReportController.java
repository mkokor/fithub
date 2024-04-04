package com.fithub.services.membership.rest.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.membership.api.ReportService;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.dao.model.MembershipEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "report", description = "Report api")
@RestController
@RequestMapping(value = "reports")
@AllArgsConstructor
public class ReportController {

		@Autowired
		private ReportService reportService;
		
		@Operation
		@GetMapping("/paid-memberships")
		public List<PaymentRecordResponse> getReportOnMemberships() throws Exception{
			return reportService.getReportOnMemberships();
		}
}
