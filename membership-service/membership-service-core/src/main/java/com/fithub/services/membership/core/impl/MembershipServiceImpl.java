package com.fithub.services.membership.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fithub.services.membership.api.MembershipService;
import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.exception.BadRequestException;
import com.fithub.services.membership.api.exception.NotFoundException;
import com.fithub.services.membership.api.model.membership.MembershipPaymentReportResponse;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.model.MembershipEntity;
import com.fithub.services.membership.dao.model.UserEntity;
import com.fithub.services.membership.dao.repository.MembershipRepository;
import com.fithub.services.membership.dao.repository.PaymentRecordRepository;
import com.fithub.services.membership.dao.repository.UserRepository;
import com.fithub.services.membership.mapper.MembershipMapper;
import com.fithub.services.membership.mapper.PaymentRecordMapper;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final PaymentRecordMapper paymentRecordMapper;
    private final MembershipMapper membershipMapper;

    @Override
    public List<PaymentRecordResponse> getPaymentRecord(Long membershipId) throws Exception {

        Optional<MembershipEntity> membershipEntity = membershipRepository.findById(membershipId);

        if (membershipEntity.isEmpty()) {
            throw new NotFoundException("The membership with the provided ID could not be found");
        }
        return paymentRecordMapper.entitiesToDtos(membershipEntity.get().getPaymentRecord());
    }

    @Override
    public MembershipPaymentReportResponse getMembershipPaymentReport(String clientUuid) throws ApiException {
        Optional<UserEntity> userEntity = userRepository.findById(clientUuid);

        if (userEntity.isEmpty()) {
            throw new NotFoundException("The user with the provided ID could not be found.");
        }

        ClientEntity clientEntity = userEntity.get().getClient();
        if (clientEntity == null) {
            throw new BadRequestException("The provided ID is associated with a coach entity.");
        }

        MembershipPaymentReportResponse membershipPaymentReport = membershipMapper
                .clientEntityToMembershipPaymentReportResponse(clientEntity);
        membershipPaymentReport.setHasDebt(!paymentRecordRepository.findUnpayedRecordsByClientId(clientEntity.getId()).isEmpty());

        return membershipPaymentReport;
    }

}