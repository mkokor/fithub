package com.fithub.services.auth.core.impl;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.fithub.services.auth.api.EmailService;
import com.fithub.services.auth.api.exception.BadRequestException;
import com.fithub.services.auth.api.exception.NotFoundException;
import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.email.EmailSendMessageRequest;
import com.fithub.services.auth.core.utils.EmailHelper;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.dao.repository.UserRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final UserRepository userRepository;
    private final EmailHelper emailHelper;
    private final Validator validator;

    @Override
    public GenericResponse sendEmailMessage(EmailSendMessageRequest emailSendMessageRequest) throws Exception {
        Set<ConstraintViolation<EmailSendMessageRequest>> violations = validator.validate(emailSendMessageRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Optional<UserEntity> user = userRepository.findById(emailSendMessageRequest.getRecipientUuid());
        if (user.isEmpty()) {
            throw new NotFoundException("The user with provided UUID could not be found.");
        }

        UserEntity userEntity = user.get();
        if (!userEntity.getEmailConfirmed()) {
            throw new BadRequestException("The user's email address has not been confirmed yet.");
        }

        emailHelper.sendPlaintextEmail(emailSendMessageRequest.getSubject(), userEntity.getEmail(), emailSendMessageRequest.getContent());

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage("The email was successfully sent.");
        return genericResponse;
    }

}