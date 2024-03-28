package com.fithub.services.training.api.model.spotify;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SpotifyTrackResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String spotifyId;

    @JsonProperty("name")
    private String title;

    @JsonProperty("preview_url")
    private String previewUrl;

    private List<ArtistResponse> artists;

    private AlbumResponse album;

}