package com.fithub.services.training.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fithub.services.training.api.model.external.spotify.SpotifyAccessTokenResponse;

@FeignClient(name = "spotify-accounts", url = "https://accounts.spotify.com/")
public interface SpotifyAccountsClient {

    @PostMapping(value = "/api/token", produces = "application/json")
    ResponseEntity<SpotifyAccessTokenResponse> retrieveAccessToken(@RequestParam("grant_type") String grantType,
            @RequestParam("client_id") String clientid, @RequestParam("client_secret") String clientSecret);

}