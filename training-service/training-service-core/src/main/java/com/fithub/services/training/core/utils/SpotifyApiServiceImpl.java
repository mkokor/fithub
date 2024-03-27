package com.fithub.services.training.core.utils;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.SpotifyApiService;
import com.fithub.services.training.api.exception.ThirdPartyApiException;
import com.fithub.services.training.api.model.spotify.SpotifyAccessTokenResponse;
import com.fithub.services.training.api.model.spotify.TracksSearchResponse;
import com.fithub.services.training.core.client.SpotifyAccountsClient;
import com.fithub.services.training.core.client.SpotifyApiClient;

import feign.FeignException.FeignClientException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpotifyApiServiceImpl implements SpotifyApiService {

    private final SpotifyAccountsClient spotifyAccountsClient;
    private final SpotifyApiClient spotifyApiClient;

    @Override
    public SpotifyAccessTokenResponse retrieveAccessToken(String grantType, String clientId, String clientSecret)
            throws ThirdPartyApiException {
        try {
            ResponseEntity<SpotifyAccessTokenResponse> spotifyAccessTokenResponse = this.spotifyAccountsClient
                    .retrieveAccessToken(grantType, clientId, clientSecret);

            return spotifyAccessTokenResponse.getBody();
        } catch (FeignClientException exception) {
            throw new ThirdPartyApiException("An error occured while trying to retrieve Spotify access token.");
        }
    }

    @Override
    public TracksSearchResponse search(String accessToken, String searchTerm, List<String> expectedContentTypes, Integer pageNumber,
            Integer pageSize) throws ThirdPartyApiException {
        try {
            ResponseEntity<TracksSearchResponse> tracksWrapperResponse = spotifyApiClient.search(accessToken, searchTerm,
                    String.join(",", expectedContentTypes), pageNumber, pageSize);

            return tracksWrapperResponse.getBody();
        } catch (FeignClientException exception) {
            throw new ThirdPartyApiException("An error occured while trying to search Spotify.");
        }
    }

}