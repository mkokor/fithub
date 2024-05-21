package com.fithub.services.auth.core.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.fithub.services.auth.api.ClientService;
import com.fithub.services.auth.api.EmailConfirmationCodeService;
import com.fithub.services.auth.api.exception.BadRequestException;
import com.fithub.services.auth.api.exception.NotFoundException;
import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.client.ClientEmailConfirmationRequest;
import com.fithub.services.auth.api.model.client.ClientSignUpRequest;
import com.fithub.services.auth.api.model.emailconfirmationcode.EmailConfirmationCodeCreateOrUpdateRequest;
import com.fithub.services.auth.core.utils.CryptoUtil;
import com.fithub.services.auth.core.utils.RabbitMQHelper;
import com.fithub.services.auth.dao.model.ClientEntity;
import com.fithub.services.auth.dao.model.CoachEntity;
import com.fithub.services.auth.dao.model.EmailConfirmationCodeEntity;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.dao.repository.ClientRepository;
import com.fithub.services.auth.dao.repository.CoachRepository;
import com.fithub.services.auth.dao.repository.EmailConfirmationCodeRepository;
import com.fithub.services.auth.dao.repository.UserRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final EmailConfirmationCodeService emailConfirmationCodeService;
    private final EmailConfirmationCodeRepository emailConfirmationCodeRepository;
    private final CoachRepository coachRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final Validator validator;
    private final RabbitMQHelper rabbitMQHelper;

    private void checkUserInformationAvailability(final String username, final String email) throws BadRequestException {
        if (userRepository.existsByUsername(username)) {
            throw new BadRequestException("The provided username is already registered.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException("The provided email is already registered.");
        }
    }

    private void checkCoachAvailability(final CoachEntity coachEntity) throws BadRequestException {
        if (coachEntity.getClients() == null) {
            return;
        }

        final Integer coachCurrentClientCapacity = coachEntity.getClients().size();

        if (coachCurrentClientCapacity == coachEntity.getClientCapacity()) {
            throw new BadRequestException("The provided coach is not available.");
        }
    }

    @Override
    public GenericResponse signUp(ClientSignUpRequest clientSignUpRequest) throws Exception {
        Set<ConstraintViolation<ClientSignUpRequest>> violations = validator.validate(clientSignUpRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        final Optional<CoachEntity> coach = coachRepository.findById(clientSignUpRequest.getCoachId());
        if (!coach.isPresent()) {
            throw new NotFoundException("The coach with provided ID could not be found.");
        }
        CoachEntity coachEntity = coach.get();

        checkUserInformationAvailability(clientSignUpRequest.getUsername(), clientSignUpRequest.getEmail());
        checkCoachAvailability(coach.get());

        final UserEntity newUser = new UserEntity();
        newUser.setUuid(UUID.randomUUID().toString());
        newUser.setFirstName(clientSignUpRequest.getFirstName());
        newUser.setLastName(clientSignUpRequest.getLastName());
        newUser.setUsername(clientSignUpRequest.getUsername());
        newUser.setEmail(clientSignUpRequest.getEmail());
        newUser.setEmailConfirmed(false);

        final String plaintextPassword = clientSignUpRequest.getPassword();
        final String hashedPassword = CryptoUtil.hash(plaintextPassword);
        newUser.setPasswordHash(hashedPassword);

        final ClientEntity newClient = new ClientEntity();
        newClient.setUser(newUser);
        newClient.setCoach(coach.get());

        coachEntity.setClientCapacity(coachEntity.getClientCapacity() + 1);

        userRepository.save(newUser);
        clientRepository.save(newClient);
        coachRepository.save(coachEntity);

        EmailConfirmationCodeCreateOrUpdateRequest emailConfirmationCodeCreateRequest = new EmailConfirmationCodeCreateOrUpdateRequest();
        emailConfirmationCodeCreateRequest.setUserEmail(newClient.getUser().getEmail());
        emailConfirmationCodeService.createOrUpdateEmailConfirmationCode(emailConfirmationCodeCreateRequest);

        rabbitMQHelper.sendUserRegistrationEventToQueue(newUser);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage(String.format("The email confirmation code is successfully sent to %s.", newUser.getEmail()));
        return genericResponse;
    }

    @Override
    public GenericResponse confimEmailAddress(ClientEmailConfirmationRequest clientEmailConfirmationRequest) throws Exception {
        Set<ConstraintViolation<ClientEmailConfirmationRequest>> violations = validator.validate(clientEmailConfirmationRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Optional<UserEntity> user = userRepository.findByEmail(clientEmailConfirmationRequest.getEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("The user with the provided email address could not be found.");
        }
        UserEntity userEntity = user.get();

        if (userEntity.getEmailConfirmed()) {
            throw new BadRequestException("The provided email address is already confirmed.");
        }

        Optional<EmailConfirmationCodeEntity> emailConfirmationCode = emailConfirmationCodeRepository.findByUser(userEntity);
        if (emailConfirmationCode.isEmpty()) {
            throw new BadRequestException("None confirmation code was issued to this user.");
        }
        EmailConfirmationCodeEntity emailConfirmationCodeEntity = emailConfirmationCode.get();

        if (!CryptoUtil.compare(clientEmailConfirmationRequest.getConfirmationCode(), emailConfirmationCodeEntity.getCodeHash())) {
            throw new BadRequestException("The provided confirmation code does not match the existing one.");
        }
        if (emailConfirmationCodeEntity.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("This confirmation code has expired.");
        }

        userEntity.setEmailConfirmed(true);

        userRepository.save(userEntity);
        emailConfirmationCodeRepository.delete(emailConfirmationCodeEntity);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage("The email address was successfully confirmed.");
        return genericResponse;
    }

}