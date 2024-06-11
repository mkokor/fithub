package com.fithub.services.mealplan.rest.mealplan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.exception.ApiException;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "mealplan", description = "Meal Plan API")
@RestController
@RequestMapping(value = "mealplan")
@RequiredArgsConstructor
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @Operation(summary = "Get meal plan by client UUID")
    @GetMapping("/{clientUuid}")
    public ResponseEntity<MealPlanResponse> getMealPlanByClientUuid(@PathVariable final String clientUuid) throws ApiException {
        return new ResponseEntity<>(mealPlanService.getMealPlanByClientUuid(clientUuid), HttpStatus.OK);
    }

    @Operation(summary = "Get meal plan for client")
    @GetMapping
    public ResponseEntity<MealPlanResponse> getMealPlanClient() {
        return new ResponseEntity<>(mealPlanService.getMealPlanClient(), HttpStatus.OK);
    }

}