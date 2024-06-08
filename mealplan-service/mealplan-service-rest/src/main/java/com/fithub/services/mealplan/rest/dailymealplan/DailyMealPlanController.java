package com.fithub.services.mealplan.rest.dailymealplan;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.mealplan.api.DailyMealPlanService;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "daily-mealplan", description = "Daily Meal Plan API")
@RestController
@RequestMapping(value = "daily-mealplan")
@RequiredArgsConstructor
public class DailyMealPlanController {

    private final DailyMealPlanService dailyMealPlanService;

    @Operation(summary = "Update daily meal plan")
    @PutMapping("/{id}")
    public ResponseEntity<DailyMealPlanResponse> update(@PathVariable final Long id,
            @RequestBody @Valid final DailyMealPlanUpdateRequest dailyMealPlanUpdateRequest) throws Exception {
        dailyMealPlanUpdateRequest.setId(id);
        return new ResponseEntity<>(dailyMealPlanService.update(dailyMealPlanUpdateRequest), HttpStatus.OK);
    }

}