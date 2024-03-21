package com.fithub.services.meal_plan.rest.coach;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.meal_plan.api.CoachService;
import com.fithub.services.meal_plan.api.model.coach.CoachResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "coach", description = "Coach API")
@RestController
@RequestMapping(value = "coach")
@AllArgsConstructor
public class CoachController {

    private final CoachService appointmentService;

    @Operation(summary = "Get all coaches")
    @GetMapping
    public List<CoachResponse> getAll() {
        return appointmentService.getAll();
    }

}