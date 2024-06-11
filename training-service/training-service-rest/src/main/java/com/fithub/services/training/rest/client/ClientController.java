package com.fithub.services.training.rest.client;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.CoachService;
import com.fithub.services.training.api.model.client.ClientResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "client", description = "Client API")
@RestController
@RequestMapping(value = "client", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ClientController {

    private final CoachService coachService;

    @Operation(summary = "Get clients")
    @GetMapping
    public ResponseEntity<List<ClientResponse>> getClients() throws Exception {
        return new ResponseEntity<>(coachService.getClients(), HttpStatus.OK);
    }

}