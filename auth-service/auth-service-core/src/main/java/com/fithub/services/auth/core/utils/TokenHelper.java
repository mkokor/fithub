package com.fithub.services.auth.core.utils;

import java.security.Key;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fithub.services.auth.api.enums.AccessTokenClaimType;
import com.fithub.services.auth.api.enums.Role;
import com.fithub.services.auth.dao.model.RefreshTokenEntity;
import com.fithub.services.auth.dao.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenHelper {

    @Value("${access-token.secret}")
    private String accessTokenSecret;

    @Value("${access-token.expiration.minutes}")
    private Integer accessTokenExpirationMinutes;

    @Value("${refresh-token.expiration.hours}")
    private Integer refreshTokenExpirationHours;

    public static String generateConfirmationCode() {
        Random randomNumberGenerator = new Random();

        Integer confirmationCode = 100000 + randomNumberGenerator.nextInt(900000);
        return String.valueOf(confirmationCode);
    }

    private static Claims configureAccessTokenClaims(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();

        claims.put(AccessTokenClaimType.EMAIL.getValue(), user.getEmail());
        claims.put(AccessTokenClaimType.USERNAME.getValue(), user.getUsername());

        String role = Role.COACH.getValue();
        if (user.getClient() == null) {
            role = Role.CLIENT.getValue();
        }
        claims.put(AccessTokenClaimType.ROLE.getValue(), role);

        return Jwts.claims(claims);
    }

    private Key generateSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(accessTokenSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private String generateJwt(UserEntity userEntity, Integer tokenExpirationMinutes) {
        Long currentMilliseconds = System.currentTimeMillis();

        final JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(configureAccessTokenClaims(userEntity));
        jwtBuilder.setSubject(userEntity.getUsername());
        jwtBuilder.setIssuedAt(new Date(currentMilliseconds));
        jwtBuilder.setExpiration(new Date(currentMilliseconds + tokenExpirationMinutes * 60 * 1000));
        jwtBuilder.signWith(generateSignKey(), SignatureAlgorithm.HS256);

        return jwtBuilder.compact();
    }

    public String generateAccessToken(UserEntity user) {
        return generateJwt(user, accessTokenExpirationMinutes);
    }

    public Pair<RefreshTokenEntity, String> generateRefreshToken(UserEntity userEntity) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[64];
        secureRandom.nextBytes(randomBytes);
        final String refreshTokenValue = generateJwt(userEntity, refreshTokenExpirationHours * 60);

        RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity();
        refreshTokenEntity.setTokenHash(CryptoUtil.hash(refreshTokenValue));
        refreshTokenEntity.setUser(userEntity);
        refreshTokenEntity.setExpirationDate(LocalDateTime.now().plusHours(refreshTokenExpirationHours));

        return new Pair<>(refreshTokenEntity, refreshTokenValue);
    }

    public String getUsernameFromJwt(String jwt) {
        var jwtParse = Jwts.parserBuilder().setSigningKey(generateSignKey()).build().parse(jwt);
        Claims claims = (Claims) jwtParse.getBody();
        return claims.getSubject();
    }

}