package com.fithub.services.training.core.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "spotify-api", url = "")
public interface SpotifyApiClient {

}