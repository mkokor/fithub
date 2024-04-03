package com.fithub.services.training.api.model.song;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties for a response object of a song request")
public class SongRequestResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The ID of created song request")
    private Long id;

}