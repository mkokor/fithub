package com.fithub.services.membership.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.membership.api.model.membership.MembershipPaymentReportResponse;
import com.fithub.services.membership.api.model.membership.MembershipResponse;
import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.model.MembershipEntity;

@Mapper(componentModel = "spring")
public interface MembershipMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "client.id", target = "clientId")
    MembershipResponse entityToDto(MembershipEntity membershipEntity);

    List<MembershipResponse> entitiesToDtos(List<MembershipEntity> membershipEntities);

    @Mapping(source = "clientEntity.user.uuid", target = "client.uuid")
    @Mapping(source = "clientEntity.user.firstName", target = "client.firstName")
    @Mapping(source = "clientEntity.user.lastName", target = "client.lastName")
    @Mapping(source = "clientEntity.user.email", target = "client.email")
    MembershipPaymentReportResponse clientEntityToMembershipPaymentReportResponse(ClientEntity clientEntity);

}