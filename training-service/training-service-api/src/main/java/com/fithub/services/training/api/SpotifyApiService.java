package com.fithub.services.training.api;

import java.util.List;

import com.fithub.services.training.api.exception.ThirdPartyApiException;
import com.fithub.services.training.api.model.spotify.SpotifyAccessTokenResponse;
import com.fithub.services.training.api.model.spotify.SpotifyTrackResponse;
import com.fithub.services.training.api.model.spotify.TracksSearchResponse;

public interface SpotifyApiService {

    SpotifyAccessTokenResponse retrieveAccessToken(String grantType, String clientId, String clientSecret) throws ThirdPartyApiException;

    TracksSearchResponse search(String accessToken, String searchTerm, List<String> expectedContentTypes, Integer pageNumber,
            Integer pageSize) throws ThirdPartyApiException;

    SpotifyTrackResponse getTrack(String accessToken, String trackId) throws ThirdPartyApiException;

}