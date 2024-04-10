package com.fithub.services.membership.rest.coach;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.membership.api.CoachService;
import com.fithub.services.membership.api.model.client.ClientResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "coach", description = "Coach API")
@RestController
@RequestMapping(value = "coach")
@AllArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @Operation(summary = "Get clients")
    @GetMapping(value = "/{id}/clients")
    public ResponseEntity<List<ClientResponse>> getClients(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(coachService.getClients(id), HttpStatus.OK);
    }

}