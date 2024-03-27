package com.fithub.services.training.core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.SpotifyApiService;
import com.fithub.services.training.api.model.spotify.SpotifyAccessTokenResponse;
import com.fithub.services.training.core.client.SpotifyAccountsClient;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpotifyApiServiceImpl implements SpotifyApiService {

    private final SpotifyAccountsClient spotifyAccountsClient;

    @Override
    public SpotifyAccessTokenResponse retrieveAccessToken(String grantType, String clientId, String clientSecret) {
        ResponseEntity<SpotifyAccessTokenResponse> spotifyAccessTokenResponse = this.spotifyAccountsClient.retrieveAccessToken(grantType,
                clientId, clientSecret);

        if (spotifyAccessTokenResponse.getStatusCode().equals(HttpStatus.OK)) {
            // Error has occured!
        }

        return spotifyAccessTokenResponse.getBody();
    }

}