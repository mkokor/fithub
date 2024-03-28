package com.fithub.services.membership.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.dao.model.PaymentRecordEntity;

@Mapper(componentModel = "spring")
public interface PaymentRecordMapper {
	
	@Mapping(source = "membership.id", target = "membershipId")
	public PaymentRecordResponse entityToDto(PaymentRecordEntity paymentRecordEntity);
	
	public List<PaymentRecordResponse> entitiesToDtos(List<PaymentRecordEntity> paymentRecordEntities);
	
		
}
