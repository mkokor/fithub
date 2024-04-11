package com.fithub.services.training.rest.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.ClientService;
import com.fithub.services.training.api.exception.ApiException;
import com.fithub.services.training.api.model.client.CoachChangeRequest;
import com.fithub.services.training.api.model.client.CoachChangeResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "client", description = "Client API")
@RestController
@RequestMapping(value = "client", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @Operation(summary = "Change coach")
    @PostMapping("/coach-change")
    public ResponseEntity<CoachChangeResponse> changeCoach(@RequestBody CoachChangeRequest coachChangeRequest) throws ApiException {
        return new ResponseEntity<>(clientService.changeCoach(coachChangeRequest), HttpStatus.OK);
    }

}