package com.fithub.services.auth.rest.client;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.auth.api.ClientService;
import com.fithub.services.auth.api.model.client.ClientResponse;
import com.fithub.services.auth.api.model.client.ClientSignUpRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "client", description = "Client API")
@RestController
@RequestMapping(value = "client")
@AllArgsConstructor
public class ClientController {

    private final Environment environment;
    private final ClientService clientService;

    @Operation(summary = "Sign up a new client")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<ClientResponse> signUp(@Valid @RequestBody ClientSignUpRequest clientSignUpRequest) throws Exception {
        return new ResponseEntity<>(clientService.signUp(clientSignUpRequest), HttpStatus.OK);
    }

    @GetMapping(value = "/load-balance/test")
    public ResponseEntity<String> testLoadBalancing() {
        return new ResponseEntity<>(environment.getProperty("local.server.port"), HttpStatus.OK);
    }

}