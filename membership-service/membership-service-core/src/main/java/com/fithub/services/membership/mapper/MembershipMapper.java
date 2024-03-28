package com.fithub.services.membership.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fithub.services.membership.api.model.membership.MembershipResponse;
import com.fithub.services.membership.dao.model.MembershipEntity;

@Mapper(componentModel = "spring")
public interface MembershipMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "client.id", target = "clientId")
    public MembershipResponse entityToDto(MembershipEntity membershipEntity);

    public List<MembershipResponse> entitiesToDtos(List<MembershipEntity> membershipEntities);

}