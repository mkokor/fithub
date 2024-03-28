package com.fithub.services.training.core.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.SongService;
import com.fithub.services.training.api.SpotifyApiService;
import com.fithub.services.training.api.exception.BadRequestException;
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

    private String findAlbumCoverImage(String songSpotifyId, List<SpotifyTrackResponse> tracks) {
        List<List<AlbumImageResponse>> albumImages = tracks.stream().filter(track -> track.getSpotifyId().equals(songSpotifyId))
                .map(SpotifyTrackResponse::getAlbum).filter(Objects::nonNull).map(AlbumResponse::getImages).filter(Objects::nonNull)
                .collect(Collectors.toList());

        return !albumImages.isEmpty() ? albumImages.get(0).get(0).getUrl() : null;
    }

    @Override
    public List<SongSearchResponse> search(Integer pageNumber, Integer pageSize, String songTitleSearchTerm) throws Exception {
        if (songTitleSearchTerm == null || songTitleSearchTerm.length() < 2) {
            throw new BadRequestException("Song search term must contain at least 2 characters.");
        }

        TracksSearchResponse tracksWrapperResponse = new TracksSearchResponse();
        try {
            tracksWrapperResponse = spotifyApiService.search(spotifyAccessToken, songTitleSearchTerm, List.of("track"), pageNumber,
                    pageSize);
        } catch (ThirdPartyApiException exception) {
            updateSpotifyAuthentication();

            tracksWrapperResponse = spotifyApiService.search(spotifyAccessToken, songTitleSearchTerm, List.of("track"), pageNumber,
                    pageSize);
        }

        List<SpotifyTrackResponse> spotifyTracks = tracksWrapperResponse.getTracks().getItems();
        List<SongSearchResponse> songSearchResults = songMapper.spotifyTrackResponsesToSongSearchResponses(spotifyTracks);

        for (SongSearchResponse songSearchResult : songSearchResults) {
            songSearchResult.setAlbumCoverImage(findAlbumCoverImage(songSearchResult.getSpotifyId(), spotifyTracks));
        }

        return songSearchResults;
    }

}