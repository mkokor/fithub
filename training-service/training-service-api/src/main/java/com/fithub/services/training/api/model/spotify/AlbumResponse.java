package com.fithub.services.training.api.model.spotify;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class AlbumResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<AlbumImageResponse> images;

}