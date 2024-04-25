package com.fithub.services.mealplan.rest.mealplan;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.exception.ApiException;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "mealplan", description = "Meal Plan API")
@RestController
@RequestMapping(value = "mealplan")
@AllArgsConstructor

public class MealPlanController {
	
	private final MealPlanService mealPlanService;
	
    @Operation(summary = "Get daily meals for a meal plan by ID")
    @GetMapping("/{mealPlanId}/dailymeals")
    public ResponseEntity<List<DailyMealPlanResponse>> getDailyMealByDay(@PathVariable Long mealPlanId) throws Exception {
        return new ResponseEntity<>(mealPlanService.getDailyMealByDay(mealPlanId), HttpStatus.OK);
    }
	

}
