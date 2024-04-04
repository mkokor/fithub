package com.fithub.services.membership.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.membership.api.MembershipService;
import com.fithub.services.membership.api.exception.NotFoundException;
import com.fithub.services.membership.api.model.client.ClientResponse;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.dao.repository.MembershipRepository;
import com.fithub.services.membership.dao.repository.PaymentRecordRepository;
import com.fithub.services.membership.mapper.ClientMapper;
import com.fithub.services.membership.mapper.PaymentRecordMapper;
import com.fithub.services.membership.dao.model.MembershipEntity;
import com.fithub.services.membership.dao.model.PaymentRecordEntity;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MembershipServiceImpl implements MembershipService {
	
	private final MembershipRepository membershipRepository;
	private final PaymentRecordMapper paymentRecordMapper;
	//private final PaymentRecordRepository paymentRecordRepository;
	//private final ClientMapper clientMapper;

	@Override
	public List<PaymentRecordResponse> getPaymentRecord(Long membershipId) throws Exception {
		
		Optional<MembershipEntity> membershipEntity = membershipRepository.findById(membershipId);
		
		if (membershipEntity.isEmpty()) {
			throw new NotFoundException("The membership with the provided ID could not be found");
		}
		return paymentRecordMapper.entitiesToDtos(membershipEntity.get().getPaymentRecord());
	}

	/*@Override
	public void updatePaymentRecord(PaymentRecordEntity paymentRecordEntity) {
		
	}*/

	
	 
}
