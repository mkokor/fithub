package com.fithub.services.mealplan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "fithub-auth-service")
public interface AuthServiceClient {

    @GetMapping(value = "client/load-balance/test")
    ResponseEntity<String> testLoadBalancing();

}