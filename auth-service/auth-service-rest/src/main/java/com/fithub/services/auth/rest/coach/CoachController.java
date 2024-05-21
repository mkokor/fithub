package com.fithub.services.auth.rest.coach;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.auth.api.CoachService;
import com.fithub.services.auth.api.coach.CoachLoV;
import com.fithub.services.auth.api.coach.CoachResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "coach", description = "Coach API")
@RestController
@RequestMapping(value = "coach", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @Operation(summary = "Get available coaches")
    @GetMapping
    public ResponseEntity<List<CoachLoV>> getAvailableCoaches() throws Exception {
        return new ResponseEntity<>(coachService.getAvailableCoaches(), HttpStatus.OK);
    }

    @Operation(summary = "Get coach by ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CoachResponse> getCoachById(@PathVariable final Long id) throws Exception {
        return new ResponseEntity<>(coachService.getCoachById(id), HttpStatus.OK);
    }

}