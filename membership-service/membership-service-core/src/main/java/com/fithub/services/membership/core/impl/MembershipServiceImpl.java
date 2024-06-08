package com.fithub.services.membership.core.impl;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fithub.services.membership.api.MembershipService;
import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.exception.BadRequestException;
import com.fithub.services.membership.api.exception.NotFoundException;
import com.fithub.services.membership.api.exception.UnauthorizedException;
import com.fithub.services.membership.api.model.membership.MembershipCreateRequest;
import com.fithub.services.membership.api.model.membership.MembershipPaymentReportResponse;
import com.fithub.services.membership.api.model.membership.MembershipResponse;
import com.fithub.services.membership.core.context.UserContext;
import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.model.CoachEntity;
import com.fithub.services.membership.dao.model.MembershipEntity;
import com.fithub.services.membership.dao.model.UserEntity;
import com.fithub.services.membership.dao.repository.MembershipRepository;
import com.fithub.services.membership.dao.repository.PaymentRecordRepository;
import com.fithub.services.membership.dao.repository.UserRepository;
import com.fithub.services.membership.mapper.MembershipMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final UserRepository userRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final MembershipMapper membershipMapper;
    private final Validator validator;

    private void validateClientCoachRelationship(final ClientEntity clientEntity, final CoachEntity coachEntity)
            throws UnauthorizedException {
        if (!clientEntity.getCoach().getId().equals(coachEntity.getId())) {
            throw new UnauthorizedException("The coach and the client are not related.");
        }
    }

    @Override
    public MembershipPaymentReportResponse getMembershipPaymentReport(String clientUuid) throws ApiException {
        final UserEntity coachUser = UserContext.getCurrentContext().getUser();
        final CoachEntity coach = coachUser.getCoach();

        Optional<UserEntity> userEntity = userRepository.findById(clientUuid);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("The user with the provided ID could not be found.");
        }

        ClientEntity clientEntity = userEntity.get().getClient();
        if (clientEntity == null) {
            throw new BadRequestException("The provided ID is associated with a coach entity.");
        }
        validateClientCoachRelationship(clientEntity, coach);

        MembershipPaymentReportResponse membershipPaymentReport = membershipMapper
                .clientEntityToMembershipPaymentReportResponse(clientEntity);
        membershipPaymentReport.setHasDebt(!paymentRecordRepository.findUnpayedRecordsByClientId(clientEntity.getId()).isEmpty());

        return membershipPaymentReport;
    }

    @Override
    public MembershipResponse createClientMembership(MembershipCreateRequest membershipCreateRequest) throws Exception {
        Set<ConstraintViolation<MembershipCreateRequest>> violations = validator.validate(membershipCreateRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        final UserEntity coachUser = UserContext.getCurrentContext().getUser();
        final CoachEntity coachEntity = coachUser.getCoach();

        final Optional<UserEntity> client = userRepository.findById(membershipCreateRequest.getClientUuid());
        if (client.isEmpty()) {
            throw new NotFoundException("The client with the provided UUID could not be found.");
        }

        final UserEntity clientUser = client.get();
        if (Objects.nonNull(clientUser.getCoach())) {
            throw new BadRequestException("The provided UUID is assigned to coach user.");
        }

        if (membershipRepository.existsByClient(clientUser.getClient())) {
            throw new BadRequestException("The client already has the membership.");
        }

        final ClientEntity clientEntity = clientUser.getClient();
        validateClientCoachRelationship(clientEntity, coachEntity);

        MembershipEntity membershipEntity = new MembershipEntity();
        membershipEntity.setClient(clientEntity);
        membershipEntity.setMonthlyAmount(membershipCreateRequest.getMonthlyAmount());
        membershipRepository.save(membershipEntity);

        return membershipMapper.entityToDto(membershipEntity);
    }

}