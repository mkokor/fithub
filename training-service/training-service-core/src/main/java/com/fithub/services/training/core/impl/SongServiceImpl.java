package com.fithub.services.training.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.SongService;
import com.fithub.services.training.api.SpotifyApiService;
import com.fithub.services.training.api.model.spotify.SpotifyAccessTokenResponse;

@Service
public class SongServiceImpl implements SongService {

    @Value("${spotify.client_id}")
    private String spotifyClientId;

    @Value("${spotify.client_secret}")
    private String spotifyClientSecret;

    private final SpotifyApiService spotifyApiService;

    @Autowired
    public SongServiceImpl(SpotifyApiService spotifyApiService) {
        this.spotifyApiService = spotifyApiService;
    }

    @Override
    public String search(String songTitleSearchTerm) {
        SpotifyAccessTokenResponse spotifyAccessTokenResponse = this.spotifyApiService.retrieveAccessToken("client_credentials",
                spotifyClientId, spotifyClientSecret);

        return songTitleSearchTerm;
    }

}