package com.fithub.services.training.api.model.external.spotify;

import java.io.Serializable;

import lombok.Data;

@Data
public class TracksSearchResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private TracksWrapperResponse tracks;

}