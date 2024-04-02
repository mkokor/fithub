package com.fithub.services.training.api.model.song;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "The properties for creation of a song request")
public class SongRequestCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "The Spotify ID of the song")
    @NotBlank(message = "The spotify ID of a song is required.")
    private String songSpotifyId;

    @Schema(description = "The ID of an appointment with which the song request will be associated.")
    @NotNull(message = "The appointment ID is required.")
    private Long appointmentId;

}