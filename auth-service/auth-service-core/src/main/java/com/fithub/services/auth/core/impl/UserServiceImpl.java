package com.fithub.services.auth.core.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fithub.services.auth.api.UserService;
import com.fithub.services.auth.api.enums.Role;
import com.fithub.services.auth.api.exception.ApiException;
import com.fithub.services.auth.api.exception.BadRequestException;
import com.fithub.services.auth.api.exception.ForbiddenException;
import com.fithub.services.auth.api.exception.NotFoundException;
import com.fithub.services.auth.api.exception.UnauthorizedException;
import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.passwordresetcode.PasswordResetCodeRequest;
import com.fithub.services.auth.api.model.user.PasswordResetRequest;
import com.fithub.services.auth.api.model.user.UserAccessTokenVerificationResponse;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${password.reset.code.expiration.minutes}")
    private Integer passwordResetCodeExpirationMinutes;

    @Value("${refresh-token.expiration.hours}")
    private Integer refreshTokenExpirationHours;

    @Value("${refresh-token.cookie.name}")
    private String refreshTokenCookieName;

    private final TokenHelper tokenHelper;
    private final EmailHelper emailHelper;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordResetCodeRepository passwordResetCodeRepository;
    private final Validator validator;

    private static Role getRole(UserEntity userEntity) {
        return userEntity.getClient() != null ? Role.CLIENT : Role.COACH;
    }

    private Cookie createCookie(final String identifier, final String value, final Boolean isHttpOnly, final int expiration) {
        Cookie cookie = new Cookie(identifier, value);
        cookie.setHttpOnly(isHttpOnly);
        cookie.setMaxAge(expiration);

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
        userSignInResponse.setUsername(userEntity.getUsername());

        final Pair<RefreshTokenEntity, String> refreshToken = tokenHelper.generateRefreshToken(userEntity);
        httpResponse.addCookie(createCookie(refreshTokenCookieName, refreshToken.getValue1(), true,
                (int) Duration.ofHours(refreshTokenExpirationHours).getSeconds()));
        refreshTokenRepository.save(refreshToken.getValue0());

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

    private String getRefreshTokenFromCookie(HttpServletRequest httpRequest) {
        Cookie[] cookies = httpRequest.getCookies();

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(refreshTokenCookieName)) {
                return cookie.getValue();
            }
        }

        return null;
    }

    private void removeRefreshTokenFromCookie(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        Cookie[] cookies = httpRequest.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(refreshTokenCookieName)) {
                cookie.setMaxAge(0);
                httpResponse.addCookie(cookie);
            }
        }
    }

    @Override
    public UserSignInResponse refreshAccessToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ApiException {
        String refreshTokenValue = getRefreshTokenFromCookie(httpRequest);

        if (refreshTokenValue == null) {
            throw new UnauthorizedException("The refresh token is missing.");
        }

        String tokenOwnerUsername = tokenHelper.getUsernameFromJwt(refreshTokenValue);
        Optional<UserEntity> userEntity = userRepository.findByUsername(tokenOwnerUsername);
        if (userEntity.isEmpty()) {
            throw new NotFoundException("The owner of the refresh token could not be found.");
        }

        List<RefreshTokenEntity> refreshTokenEntities = refreshTokenRepository.findByUser(userEntity.get());
        Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenEntities.stream()
                .filter(refreshToken -> CryptoUtil.compare(refreshTokenValue, refreshToken.getTokenHash())).findFirst();

        if (refreshTokenEntity.isEmpty() || refreshTokenEntity.get().getExpirationDate().isBefore(LocalDateTime.now())) {
            removeRefreshTokenFromCookie(httpRequest, httpResponse);
            throw new UnauthorizedException("The refresh token is not valid.");
        }

        RefreshTokenEntity oldRefreshToken = refreshTokenEntity.get();
        Pair<RefreshTokenEntity, String> newRefreshToken = tokenHelper.generateRefreshToken(userEntity.get());

        refreshTokenRepository.delete(oldRefreshToken);
        refreshTokenRepository.save(newRefreshToken.getValue0());

        httpResponse.addCookie(createCookie(refreshTokenCookieName, newRefreshToken.getValue1(), true,
                (int) Duration.ofHours(refreshTokenExpirationHours).getSeconds()));

        UserSignInResponse userSignInResponse = new UserSignInResponse();
        userSignInResponse.setAccessToken(tokenHelper.generateAccessToken(userEntity.get()));
        userSignInResponse.setRole(getRole(userEntity.get()).getValue());
        return userSignInResponse;
    }

    @Override
    public GenericResponse signOut(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        String refreshTokenValue = getRefreshTokenFromCookie(httpRequest);

        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setMessage("The user is successfully signed out.");

        if (refreshTokenValue == null) {
            return genericResponse;
        }

        String tokenOwnerUsername = tokenHelper.getUsernameFromJwt(refreshTokenValue);
        Optional<UserEntity> userEntity = userRepository.findByUsername(tokenOwnerUsername);
        if (userEntity.isEmpty()) {
            return genericResponse;
        }

        List<RefreshTokenEntity> refreshTokenEntities = refreshTokenRepository.findByUser(userEntity.get());
        Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenEntities.stream()
                .filter(refreshToken -> CryptoUtil.compare(refreshTokenValue, refreshToken.getTokenHash())).findFirst();
        if (refreshTokenEntity.isPresent()) {
            refreshTokenRepository.delete(refreshTokenEntity.get());
        }

        removeRefreshTokenFromCookie(httpRequest, httpResponse);
        return genericResponse;
    }

    @Override
    public UserAccessTokenVerificationResponse verifyAccessToken() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final UserEntity user = (UserEntity) authentication.getPrincipal();
        final String role = ((SimpleGrantedAuthority) authentication.getAuthorities().toArray()[0]).getAuthority();

        UserAccessTokenVerificationResponse userAccessTokenVerificationResponse = new UserAccessTokenVerificationResponse();
        userAccessTokenVerificationResponse.setUserUuid(user.getUuid());
        userAccessTokenVerificationResponse.setUsername(user.getUsername());
        userAccessTokenVerificationResponse.setRole(role);

        return userAccessTokenVerificationResponse;
    }

}