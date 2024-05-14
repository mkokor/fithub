package com.fithub.services.auth.core.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fithub.services.auth.api.EmailConfirmationCodeService;
import com.fithub.services.auth.api.exception.ApiException;
import com.fithub.services.auth.api.exception.BadRequestException;
import com.fithub.services.auth.api.exception.NotFoundException;
import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.emailconfirmationcode.EmailConfirmationCodeCreateOrUpdateRequest;
import com.fithub.services.auth.core.utils.CryptoUtil;
import com.fithub.services.auth.core.utils.EmailHelper;
import com.fithub.services.auth.core.utils.TokenHelper;
import com.fithub.services.auth.dao.model.EmailConfirmationCodeEntity;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.dao.repository.EmailConfirmationCodeRepository;
import com.fithub.services.auth.dao.repository.UserRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

@Service
public class EmailConfirmationCodeImpl implements EmailConfirmationCodeService {

    @Value("${email.confirmation.code.expiration.minutes}")
    private Integer emailConfirmationCodeExpirationMinutes;

    private final EmailHelper emailHelper;
    private final UserRepository userRepository;
    private final EmailConfirmationCodeRepository emailConfirmationCodeRepository;
    private final Validator validator;

    public EmailConfirmationCodeImpl(EmailHelper emailHelper, UserRepository userRepository,
            EmailConfirmationCodeRepository emailConfirmationCodeRepository, Validator validator) {
        this.emailHelper = emailHelper;
        this.userRepository = userRepository;
        this.emailConfirmationCodeRepository = emailConfirmationCodeRepository;
        this.validator = validator;
    }

    private void sendEmailConfirmationMessage(String recipientEmailAddress, final String emailConfirmationCode) {
        emailHelper.sendPlaintextEmail("Email Confirmation", recipientEmailAddress, emailConfirmationCode);
    }

    private void createEmailConfirmationCode(UserEntity user, String newCode) {
        final String newCodeHash = CryptoUtil.hash(newCode);

        EmailConfirmationCodeEntity emailConfirmationCodeEntity = new EmailConfirmationCodeEntity();
        emailConfirmationCodeEntity.setUser(user);
        emailConfirmationCodeEntity.setCodeHash(newCodeHash);
        emailConfirmationCodeEntity.setExpirationDate(LocalDateTime.now().plusMinutes(emailConfirmationCodeExpirationMinutes));

        emailConfirmationCodeRepository.save(emailConfirmationCodeEntity);
    }

    private void updateEmailConfirmationCode(EmailConfirmationCodeEntity emailConfirmationCodeEntity, String newCode) {
        final String newCodeHash = CryptoUtil.hash(newCode);

        emailConfirmationCodeEntity.setCodeHash(newCodeHash);
        emailConfirmationCodeEntity.setExpirationDate(LocalDateTime.now().plusMinutes(emailConfirmationCodeExpirationMinutes));

        emailConfirmationCodeRepository.save(emailConfirmationCodeEntity);
    }

    @Override
    public GenericResponse createOrUpdateEmailConfirmationCode(
            EmailConfirmationCodeCreateOrUpdateRequest emailConfirmationCodeCreateOrUpdateRequest) throws ApiException {
        Set<ConstraintViolation<EmailConfirmationCodeCreateOrUpdateRequest>> violations = validator
                .validate(emailConfirmationCodeCreateOrUpdateRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Optional<UserEntity> user = userRepository.findByEmail(emailConfirmationCodeCreateOrUpdateRequest.getUserEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("The user with the provided email address could not be found.");
        }

        if (user.get().getEmailConfirmed()) {
            throw new BadRequestException("The user with the provided email address already has confirmed it.");
        }

        final String emailConfirmationCode = TokenHelper.generateConfirmationCode();

        Optional<EmailConfirmationCodeEntity> emailConfirmationCodeEntity = emailConfirmationCodeRepository.findByUser(user.get());
        if (emailConfirmationCodeEntity.isEmpty()) {
            createEmailConfirmationCode(user.get(), emailConfirmationCode);
        } else {
            updateEmailConfirmationCode(emailConfirmationCodeEntity.get(), emailConfirmationCode);
        }

        sendEmailConfirmationMessage(emailConfirmationCodeCreateOrUpdateRequest.getUserEmail(), emailConfirmationCode);

        GenericResponse response = new GenericResponse();
        response.setMessage(String.format("The email confirmation code is successfully sent to %s.",
                emailConfirmationCodeCreateOrUpdateRequest.getUserEmail()));
        return response;
    }

}