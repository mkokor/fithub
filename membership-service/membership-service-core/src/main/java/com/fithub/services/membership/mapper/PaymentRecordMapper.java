package com.fithub.services.membership.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.dao.model.PaymentRecordEntity;

@Mapper(componentModel = "spring")
public interface PaymentRecordMapper {

    @Mapping(source = "membership.client.user.uuid", target = "client.uuid")
    @Mapping(source = "membership.client.user.firstName", target = "client.firstName")
    @Mapping(source = "membership.client.user.lastName", target = "client.lastName")
    PaymentRecordResponse entityToDto(PaymentRecordEntity paymentRecordEntity);

    List<PaymentRecordResponse> entitiesToDtos(List<PaymentRecordEntity> paymentRecordEntities);

}