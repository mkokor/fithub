package com.fithub.services.mealplan.rest.client;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.mealplan.api.ClientService;
import com.fithub.services.mealplan.api.CoachService;
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.coach.CoachResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;
import com.fithub.services.mealplan.api.model.mealplan.NewMealPlanRequest;
import com.fithub.services.mealplan.api.model.user.NewUserRequest;
import com.fithub.services.mealplan.api.model.user.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "client", description = "Client API")
@RestController
@RequestMapping(value = "client")
@AllArgsConstructor
public class ClientController {
	
	private final ClientService clientService;
    @Operation(summary = "Get meal plan of client by id")
    @GetMapping(value = "/{id}/mealplan")
	public ResponseEntity<MealPlanResponse> getMealPlan(@PathVariable Long id) throws Exception {
		return new ResponseEntity<>(clientService.getMealPlan(id), HttpStatus.OK);
	}
    
    @Operation(summary = "Get name of client by id")
    @GetMapping("/{userId}/name")
    public ResponseEntity<UserResponse> getClientNameAndLastName(@PathVariable String userId) {
        try {
            UserResponse clientNameAndLastName = clientService.getClientNameAndLastName(userId);
            //System.out.println("Coach name and last name: " + coachNameAndLastName.getFirstName() + " " + coachNameAndLastName.getLastName());
            return ResponseEntity.ok(clientNameAndLastName);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @Operation(summary = "Get daily meal plan of client by id")
    @GetMapping("/{id}/dailyMealPlan")
    public ResponseEntity<List<DailyMealPlanResponse>> getDailyMealPlan(@PathVariable Long id) {
        try {
            List<DailyMealPlanResponse> dailyMealPlan = clientService.getDailyMealPlanByClientId(id);
            return ResponseEntity.ok(dailyMealPlan);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Operation(summary = "Make meal plan for client")
    @PostMapping("/{id}/make-meal-plan")
    public ResponseEntity<MealPlanResponse> makeMealPlanForClient(@Valid @PathVariable String id) throws Exception{
    	return new ResponseEntity<>(clientService.makeMealPlanForClient(id), HttpStatus.OK);
    }
    
    @Operation(summary = "Post coach for client")
    @PostMapping("/{id}/assign-coach")
    public ResponseEntity<CoachResponse> postCoachForClient(@Valid @PathVariable String id,@Valid @RequestBody NewUserRequest newUserRequest) throws Exception{
    	return new ResponseEntity<>(clientService.postCoachForClient(id, newUserRequest), HttpStatus.OK);
    }
    
    // This method was made only for purposes of testing Ribbon load balancer.
    @GetMapping("/load-balance/test")
    public ResponseEntity<String> testLoadBalancing() {
        return new ResponseEntity<>(clientService.testLoadBalancer(), HttpStatus.OK);
    }


}
