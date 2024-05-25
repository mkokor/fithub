package com.fithub.services.training.core.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "fithub-auth-service")
public interface AuthServiceClient {

}