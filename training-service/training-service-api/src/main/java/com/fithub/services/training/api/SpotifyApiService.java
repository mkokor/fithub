package com.fithub.services.training.api;

import com.fithub.services.training.api.model.spotify.SpotifyAccessTokenResponse;

public interface SpotifyApiService {

    SpotifyAccessTokenResponse retrieveAccessToken(String grantType, String clientId, String clientSecret);

}