package com.fithub.services.training.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fithub.services.training.api.model.GenericResponse;
import com.fithub.services.training.api.model.external.EmailSendMessageRequest;

@FeignClient(name = "auth-service")
public interface AuthServiceClient {

    @PostMapping(value = "/email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<GenericResponse> sendFitHubEmailMessage(@RequestBody EmailSendMessageRequest emailSendMessageRequest);

}