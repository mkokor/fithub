package com.fithub.services.training.core.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.SongService;
import com.fithub.services.training.api.SpotifyApiService;
import com.fithub.services.training.api.exception.ThirdPartyApiException;
import com.fithub.services.training.api.model.song.SongSearchResponse;
import com.fithub.services.training.api.model.spotify.AlbumImageResponse;
import com.fithub.services.training.api.model.spotify.AlbumResponse;
import com.fithub.services.training.api.model.spotify.SpotifyAccessTokenResponse;
import com.fithub.services.training.api.model.spotify.SpotifyTrackResponse;
import com.fithub.services.training.api.model.spotify.TracksSearchResponse;
import com.fithub.services.training.mapper.SongMapper;

@Service
public class SongServiceImpl implements SongService {

    @Value("${spotify.client_id}")
    private String spotifyClientId;

    @Value("${spotify.client_secret}")
    private String spotifyClientSecret;

    private String spotifyAccessToken;
    private final SpotifyApiService spotifyApiService;
    private final SongMapper songMapper;

    @Autowired
    public SongServiceImpl(SpotifyApiService spotifyApiService, SongMapper songMapper) {
        this.spotifyApiService = spotifyApiService;
        this.songMapper = songMapper;
    }

    private void updateSpotifyAuthentication() throws ThirdPartyApiException {
        SpotifyAccessTokenResponse spotifyAccessTokenResponse = this.spotifyApiService.retrieveAccessToken("client_credentials",
                spotifyClientId, spotifyClientSecret);

        spotifyAccessToken = String.format("%s %s", spotifyAccessTokenResponse.getTokenType(), spotifyAccessTokenResponse.getAccessToken());
    }

    @Override
    public List<SongSearchResponse> search(Integer pageNumber, Integer pageSize, String songTitleSearchTerm) throws ThirdPartyApiException {
        TracksSearchResponse tracksWrapperResponse;

        try {
            tracksWrapperResponse = spotifyApiService.search(spotifyAccessToken, songTitleSearchTerm, List.of("track"), pageNumber,
                    pageSize);
        } catch (ThirdPartyApiException exception) {
            updateSpotifyAuthentication();

            tracksWrapperResponse = spotifyApiService.search(spotifyAccessToken, songTitleSearchTerm, List.of("track"), pageNumber,
                    pageSize);
        }

        List<SongSearchResponse> songSearchResults = songMapper
                .spotifyTrackResponsesToSongSearchResponses(tracksWrapperResponse.getTracks().getItems());

        for (SongSearchResponse songSearchResult : songSearchResults) {
            List<List<AlbumImageResponse>> albumImages = tracksWrapperResponse.getTracks().getItems().stream()
                    .filter(track -> track.getSpotifyId().equals(songSearchResult.getSpotifyId())).map(SpotifyTrackResponse::getAlbum)
                    .map(AlbumResponse::getImages).collect(Collectors.toList());

            songSearchResult.setAlbumCoverImage(!albumImages.isEmpty() ? albumImages.get(0).get(0).getUrl() : null);
        }

        return songSearchResults;
    }

}