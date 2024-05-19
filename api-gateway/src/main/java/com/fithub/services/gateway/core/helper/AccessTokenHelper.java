package com.fithub.services.gateway.core.helper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fithub.services.gateway.core.model.UserAccessTokenVerificationResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AccessTokenHelper {

    @Value("${auth.service.location}")
    private String authServiceLocation;

    private final RestTemplate restTemplate;

    public static boolean isValidBearerToken(String token) {
        if (token == null) {
            return false;
        }

        String regex = "^Bearer .+$";
        return token.matches(regex);
    }

    public UserAccessTokenVerificationResponse verifyAccessToken(final String accessToken) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        final String url = String.format("%s/user/access-token/verification", authServiceLocation);
        ResponseEntity<UserAccessTokenVerificationResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity,
                UserAccessTokenVerificationResponse.class);

        return response.getBody();
    }

}