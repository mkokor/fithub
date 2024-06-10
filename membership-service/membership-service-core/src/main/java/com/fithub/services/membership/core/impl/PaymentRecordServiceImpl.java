package com.fithub.services.membership.core.impl;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fithub.services.membership.api.PaymentRecordService;
import com.fithub.services.membership.api.exception.ApiException;
import com.fithub.services.membership.api.exception.BadRequestException;
import com.fithub.services.membership.api.exception.NotFoundException;
import com.fithub.services.membership.api.exception.UnauthorizedException;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordPayRequest;
import com.fithub.services.membership.api.model.paymentrecord.PaymentRecordResponse;
import com.fithub.services.membership.core.context.UserContext;
import com.fithub.services.membership.dao.model.ClientEntity;
import com.fithub.services.membership.dao.model.CoachEntity;
import com.fithub.services.membership.dao.model.PaymentRecordEntity;
import com.fithub.services.membership.dao.model.UserEntity;
import com.fithub.services.membership.dao.repository.PaymentRecordRepository;
import com.fithub.services.membership.dao.repository.UserRepository;
import com.fithub.services.membership.mapper.PaymentRecordMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentRecordServiceImpl implements PaymentRecordService {

    private final UserRepository userRepository;
    private final PaymentRecordRepository paymentRecordRepository;
    private final PaymentRecordMapper paymentRecordMapper;
    private final Validator validator;

    private CoachEntity extractCoachFromAuthenticatedUser() {
        final UserEntity authenticatedUser = UserContext.getCurrentContext().getUser();
        return authenticatedUser.getCoach();
    }

    private ClientEntity validateCoachClientRelationship(final String clientUuid) throws ApiException {
        final CoachEntity coach = extractCoachFromAuthenticatedUser();

        final Optional<UserEntity> clientUser = userRepository.findById(clientUuid);
        if (clientUser.isEmpty()) {
            throw new NotFoundException("The client with the provided UUID could not be found.");
        }

        final UserEntity client = clientUser.get();
        if (Objects.nonNull(client.getCoach())) {
            throw new BadRequestException("The user with the provided UUID is coach.");
        }

        final ClientEntity clientEntity = client.getClient();
        if (!clientEntity.getCoach().getId().equals(coach.getId())) {
            throw new UnauthorizedException("The user with the provided UUID is not related with authenticated coach.");
        }

        return clientEntity;
    }

    private PaymentRecordEntity findPaymentRecordForCurrentDate(final Long membershipId) {
        final String month = LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();
        final Integer year = LocalDateTime.now().getYear();

        Optional<PaymentRecordEntity> paymentRecordEntity = paymentRecordRepository.findByYearAndMonthAndMembershipId(year, month,
                membershipId);
        return paymentRecordEntity.isEmpty() ? null : paymentRecordEntity.get();
    }

    @Override
    public PaymentRecordResponse createUnpayedRecord(String clientUuid) throws ApiException {
        final ClientEntity clientEntity = validateCoachClientRelationship(clientUuid);

        if (Objects.isNull(clientEntity.getMembership())) {
            throw new BadRequestException("The membership is not created for this client.");
        }

        PaymentRecordEntity paymentRecord = findPaymentRecordForCurrentDate(clientEntity.getMembership().getId());
        if (Objects.isNull(paymentRecord)) {
            paymentRecord = new PaymentRecordEntity();
            paymentRecord.setMembership(clientEntity.getMembership());
            paymentRecord.setMonth(LocalDateTime.now().getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase());
            paymentRecord.setYear(LocalDateTime.now().getYear());
            paymentRecord.setPaid(Boolean.FALSE);
            paymentRecordRepository.save(paymentRecord);
        }

        return paymentRecordMapper.entityToDto(paymentRecord);
    }

    @Override
    public PaymentRecordResponse setPayed(final PaymentRecordPayRequest payRequest) throws Exception {
        Set<ConstraintViolation<PaymentRecordPayRequest>> violations = validator.validate(payRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        final ClientEntity clientEntity = validateCoachClientRelationship(payRequest.getClientUuid());

        if (Objects.isNull(clientEntity.getMembership())) {
            throw new BadRequestException("The membership is not created for this client.");
        }

        Optional<PaymentRecordEntity> paymentRecord = paymentRecordRepository.findByYearAndMonthAndMembershipId(payRequest.getYear(),
                payRequest.getMonth().toUpperCase(), clientEntity.getMembership().getId());
        if (paymentRecord.isEmpty()) {
            throw new BadRequestException("The payment record for defined month does not exist.");
        }

        paymentRecord.get().setPaid(Boolean.TRUE);
        paymentRecordRepository.save(paymentRecord.get());

        return paymentRecordMapper.entityToDto(paymentRecord.get());
    }

    @Override
    public List<PaymentRecordResponse> getAll(String clientUuid) throws ApiException {
        final ClientEntity clientEntity = validateCoachClientRelationship(clientUuid);

        if (Objects.isNull(clientEntity.getMembership())) {
            throw new BadRequestException("The membership is not created for this client.");
        }

        return paymentRecordMapper.entitiesToDtos(paymentRecordRepository.findByMembershipId(clientEntity.getMembership().getId()));
    }

}