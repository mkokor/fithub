package com.fithub.services.training.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.fithub.services.training.api.model.spotify.SpotifyTrackResponse;
import com.fithub.services.training.api.model.spotify.TracksSearchResponse;

@FeignClient(name = "spotify-api", url = "https://api.spotify.com/")
public interface SpotifyApiClient {

    @GetMapping(value = "/v1/search", produces = "application/json")
    public ResponseEntity<TracksSearchResponse> search(@RequestHeader("Authorization") String accessToken,
            @RequestParam("q") String searchTerm, @RequestParam("type") String typeOfContent, @RequestParam("offset") Integer pageNumber,
            @RequestParam("limit") Integer pageSize);

    @GetMapping(value = "/v1/tracks/{trackId}")
    public ResponseEntity<SpotifyTrackResponse> getTrack(@RequestHeader("Authorization") String accessToken, @PathVariable String trackId);

}