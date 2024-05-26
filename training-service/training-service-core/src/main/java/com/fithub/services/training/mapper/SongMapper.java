package com.fithub.services.training.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.fithub.services.training.api.model.external.spotify.SpotifyTrackResponse;
import com.fithub.services.training.api.model.song.SongRequestResponse;
import com.fithub.services.training.api.model.song.SongSearchResponse;
import com.fithub.services.training.dao.model.SongRequestEntity;

@Mapper(componentModel = "spring")
public interface SongMapper {

    public SongSearchResponse spotifyTrackResponseToSongSearchResponse(SpotifyTrackResponse spotifyTrackResponse);

    public List<SongSearchResponse> spotifyTrackResponsesToSongSearchResponses(List<SpotifyTrackResponse> spotifyTracks);

    public SongRequestResponse songRequestEntityToSongRequestResponse(SongRequestEntity songRequestEntity);

}