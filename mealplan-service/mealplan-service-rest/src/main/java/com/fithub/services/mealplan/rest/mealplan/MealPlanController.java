package com.fithub.services.mealplan.rest.mealplan;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.fithub.services.mealplan.api.MealPlanService;
import com.fithub.services.mealplan.api.exception.ApiException;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.MealPlanUpdateRequest;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
	
    @Operation(summary = "Update meal plan")
    @PostMapping("/{clientId}/update")
    public ResponseEntity<List<DailyMealPlanResponse>> updateMealPlan(@Valid @PathVariable Long clientId, 
    		@Valid @RequestBody MealPlanUpdateRequest mealPlanUpdateRequest) throws Exception{
    	
    	List<DailyMealPlanResponse> response = mealPlanService.updateMealPlan(clientId, mealPlanUpdateRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    	
    }
    

}
