package com.fithub.services.training.api.model.song;

import java.io.Serializable;
import java.util.List;

import com.fithub.services.training.api.model.spotify.ArtistResponse;

import lombok.Data;

@Data
public class SongSearchResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String spotifyId;

    private String title;

    private String previewUrl;

    private List<ArtistResponse> artists;

    private String albumCoverImage;

}