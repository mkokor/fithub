package com.fithub.services.membership.core.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fithub.services.membership.api.ReportService;
import com.fithub.services.membership.api.exception.NotFoundException;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.dao.model.PaymentRecordEntity;
import com.fithub.services.membership.dao.repository.MembershipRepository;
import com.fithub.services.membership.dao.repository.PaymentRecordRepository;
import com.fithub.services.membership.mapper.PaymentRecordMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService{

	@Autowired
	private MembershipRepository membershipRepository;
	
	@Autowired
	private PaymentRecordRepository paymentRecordRepository;
	
	private PaymentRecordMapper paymentRecordMapper;
	
	
	@Override
	public List<PaymentRecordResponse> getReportOnMemberships() throws NotFoundException {
		
		//List<MembershipEntity> memberships = membershipRepository.findAll();
		List<PaymentRecordEntity> payments1 = paymentRecordRepository.findAll();
		//List<Boolean> paidStatus = new ArrayList<>();
		
		List<PaymentRecordResponse> payments = paymentRecordMapper.entitiesToDtos(payments1);
		
		
		/*for(PaymentRecordResponse payment: payments) {
			 paidStatus.add(payment.isPaid());
			
			if(paidStatus.isEmpty()) {
				throw new NotFoundException("The payment records do not exist");
			}
		}*/
		
		return payments;
		
		
	}

}
