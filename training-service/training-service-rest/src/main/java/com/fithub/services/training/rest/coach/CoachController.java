package com.fithub.services.training.rest.coach;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.training.api.CoachService;
import com.fithub.services.training.api.model.GenericResponse;
import com.fithub.services.training.api.model.coach.ClientCapacityUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "coach", description = "Coach API")
@RestController
@RequestMapping(value = "coach", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @Operation(summary = "Update client capacity")
    @PutMapping("/client-capacity")
    public ResponseEntity<GenericResponse> updateClientCapacity(@Valid @RequestBody ClientCapacityUpdateRequest clientCapacityUpdateRequest)
            throws Exception {
        return new ResponseEntity<>(coachService.updateClientCapacity(clientCapacityUpdateRequest), HttpStatus.OK);
    }

}