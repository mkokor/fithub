package com.fithub.services.training.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.fithub.services.training.api.model.song.SongSearchResponse;
import com.fithub.services.training.api.model.spotify.SpotifyTrackResponse;

@Mapper(componentModel = "spring")
public interface SongMapper {

    public SongSearchResponse spotifyTrackResponseToSongSearchResponse(SpotifyTrackResponse trackResponse);

    public List<SongSearchResponse> spotifyTrackResponsesToSongSearchResponses(List<SpotifyTrackResponse> trackResponses);

}