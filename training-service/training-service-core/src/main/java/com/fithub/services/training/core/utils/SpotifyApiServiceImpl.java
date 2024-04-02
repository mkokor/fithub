package com.fithub.services.training.core.utils;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.SpotifyApiService;
import com.fithub.services.training.api.exception.ThirdPartyApiException;
import com.fithub.services.training.api.model.spotify.SpotifyAccessTokenResponse;
import com.fithub.services.training.api.model.spotify.SpotifyTrackResponse;
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
        return handleException(() -> {
            return this.spotifyAccountsClient.retrieveAccessToken(grantType, clientId, clientSecret);
        }, "An error occurred while trying to retrieve Spotify access token.");
    }

    @Override
    public TracksSearchResponse search(String accessToken, String searchTerm, List<String> expectedContentTypes, Integer pageNumber,
            Integer pageSize) throws ThirdPartyApiException {
        return handleException(() -> {
            return spotifyApiClient.search(accessToken, searchTerm, String.join(",", expectedContentTypes), pageNumber, pageSize);
        }, "An error occurred while trying to search Spotify.");
    }

    @Override
    public SpotifyTrackResponse getTrack(String accessToken, String trackId) throws ThirdPartyApiException {
        return handleException(() -> {
            return spotifyApiClient.getTrack(accessToken, trackId);
        }, "An error occurred while trying to retrieve the track from Spotify.");
    }

    private <T> T handleException(Supplier<ResponseEntity<T>> supplier, String errorMessage) throws ThirdPartyApiException {
        try {
            return supplier.get().getBody();
        } catch (FeignClientException exception) {
            throw new ThirdPartyApiException(HttpStatus.valueOf(exception.status()), errorMessage);
        }
    }

}