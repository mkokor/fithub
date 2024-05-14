package com.fithub.services.auth.core.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fithub.services.auth.api.UserService;
import com.fithub.services.auth.api.enums.Role;
import com.fithub.services.auth.api.exception.BadRequestException;
import com.fithub.services.auth.api.exception.ForbiddenException;
import com.fithub.services.auth.api.exception.NotFoundException;
import com.fithub.services.auth.api.exception.UnauthorizedException;
import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.passwordresetcode.PasswordResetCodeRequest;
import com.fithub.services.auth.api.model.user.PasswordResetRequest;
import com.fithub.services.auth.api.model.user.UserSignInRequest;
import com.fithub.services.auth.api.model.user.UserSignInResponse;
import com.fithub.services.auth.core.utils.CryptoUtil;
import com.fithub.services.auth.core.utils.EmailHelper;
import com.fithub.services.auth.core.utils.TokenHelper;
import com.fithub.services.auth.dao.model.PasswordResetCodeEntity;
import com.fithub.services.auth.dao.model.RefreshTokenEntity;
import com.fithub.services.auth.dao.model.UserEntity;
import com.fithub.services.auth.dao.repository.PasswordResetCodeRepository;
import com.fithub.services.auth.dao.repository.RefreshTokenRepository;
import com.fithub.services.auth.dao.repository.UserRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import javafx.util.Pair;

@Service
public class UserServiceImpl implements UserService {

    @Value("${password.reset.code.expiration.minutes}")
    private Integer passwordResetCodeExpirationMinutes;

    private final TokenHelper tokenHelper;
    private final EmailHelper emailHelper;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetCodeRepository passwordResetCodeRepository;
    private final Validator validator;

    public UserServiceImpl(TokenHelper tokenHelper, UserRepository userRepository, RefreshTokenRepository refreshTokenRepository,
            PasswordResetCodeRepository passwordResetCodeRepository, Validator validator, EmailHelper emailHelper) {
        this.tokenHelper = tokenHelper;
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordResetCodeRepository = passwordResetCodeRepository;
        this.validator = validator;
        this.emailHelper = emailHelper;
    }

    private static Role getRole(UserEntity userEntity) {
        return userEntity.getClient() != null ? Role.CLIENT : Role.COACH;
    }

    private Cookie createCookie(final String identifier, final String value, final Boolean isHttpOnly) {
        Cookie cookie = new Cookie(identifier, value);
        cookie.setHttpOnly(isHttpOnly);

        return cookie;
    }

    @Override
    public UserSignInResponse signIn(UserSignInRequest userSignInRequest, HttpServletResponse httpResponse) throws Exception {
        Set<ConstraintViolation<UserSignInRequest>> violations = validator.validate(userSignInRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Optional<UserEntity> user = userRepository.findByUsername(userSignInRequest.getUsername());
        if (user.isEmpty()) {
            throw new NotFoundException("The user with the provided username could not be found.");
        }
        UserEntity userEntity = user.get();

        if (!userEntity.getEmailConfirmed()) {
            throw new ForbiddenException("The email address has not been confirmed.");
        }

        if (!CryptoUtil.compare(userSignInRequest.getPassword(), userEntity.getPasswordHash())) {
            throw new UnauthorizedException("Provided user credentials are not correct.");
        }

        UserSignInResponse userSignInResponse = new UserSignInResponse();
        userSignInResponse.setAccessToken(tokenHelper.generateAccessToken(userEntity));
        userSignInResponse.setRole(getRole(userEntity).getValue());

        final Pair<RefreshTokenEntity, String> refreshToken = tokenHelper.generateRefreshToken(userEntity);
        httpResponse.addCookie(createCookie("refresh_token", refreshToken.getValue(), true));
        refreshTokenRepository.save(refreshToken.getKey());

        return userSignInResponse;
    }

    @Override
    public GenericResponse sendPasswordResetCode(PasswordResetCodeRequest passwordResetCodeRequest) throws Exception {
        Set<ConstraintViolation<PasswordResetCodeRequest>> violations = validator.validate(passwordResetCodeRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Optional<UserEntity> user = userRepository.findByEmail(passwordResetCodeRequest.getUserEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("The user with the provided email could not be found.");
        }
        final UserEntity userEntity = user.get();

        if (!userEntity.getEmailConfirmed()) {
            throw new ForbiddenException("The email address has not been confirmed.");
        }

        Optional<PasswordResetCodeEntity> passwordResetCode = passwordResetCodeRepository.findByUser(userEntity);
        PasswordResetCodeEntity passwordResetCodeEntity = new PasswordResetCodeEntity();
        if (passwordResetCode.isPresent()) {
            passwordResetCodeEntity = passwordResetCode.get();
        }

        final String passwordResetCodeValue = TokenHelper.generateConfirmationCode();
        passwordResetCodeEntity.setCodeHash(CryptoUtil.hash(passwordResetCodeValue));
        passwordResetCodeEntity.setUser(userEntity);
        passwordResetCodeEntity.setExpirationDate(LocalDateTime.now().plusMinutes(passwordResetCodeExpirationMinutes));
        passwordResetCodeRepository.save(passwordResetCodeEntity);

        emailHelper.sendPlaintextEmail("Password Reset", userEntity.getEmail(), passwordResetCodeValue);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage(String.format("The password reset code is successfully sent to %s.", userEntity.getEmail()));
        return genericResponse;
    }

    @Override
    public GenericResponse resetPassword(PasswordResetRequest passwordResetRequest) throws Exception {
        Set<ConstraintViolation<PasswordResetRequest>> violations = validator.validate(passwordResetRequest);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Optional<UserEntity> user = userRepository.findByEmail(passwordResetRequest.getUserEmail());
        if (user.isEmpty()) {
            throw new NotFoundException("The user with the provided email could not be found.");
        }
        final UserEntity userEntity = user.get();

        if (!userEntity.getEmailConfirmed()) {
            throw new ForbiddenException("The email address has not been confirmed.");
        }

        Optional<PasswordResetCodeEntity> passwordResetCode = passwordResetCodeRepository.findByUser(userEntity);
        if (passwordResetCode.isEmpty()) {
            throw new BadRequestException(String.format("None password reset code has been issued to %s.", userEntity.getEmail()));
        }
        PasswordResetCodeEntity passwordResetCodeEntity = passwordResetCode.get();

        if (!CryptoUtil.compare(passwordResetRequest.getPasswordResetCode(), passwordResetCodeEntity.getCodeHash())) {
            throw new BadRequestException("The provided reset code does not match the existing one.");
        }
        if (passwordResetCodeEntity.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("This reset code has expired.");
        }

        userEntity.setPasswordHash(CryptoUtil.hash(passwordResetRequest.getNewPassword()));

        userRepository.save(userEntity);
        passwordResetCodeRepository.delete(passwordResetCodeEntity);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage("The password is successfully updated.");
        return genericResponse;
    }

}