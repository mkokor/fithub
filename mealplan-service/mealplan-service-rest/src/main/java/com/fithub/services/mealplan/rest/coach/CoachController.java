package com.fithub.services.mealplan.rest.coach;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fithub.services.mealplan.api.CoachService; 
import com.fithub.services.mealplan.api.exception.NotFoundException;
import com.fithub.services.mealplan.api.model.client.ClientResponse;
import com.fithub.services.mealplan.api.model.dailymealplan.DailyMealPlanResponse;
import com.fithub.services.mealplan.api.model.user.UserResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "coach", description = "Coach API")
@RestController
@RequestMapping(value = "coach")
@AllArgsConstructor
public class CoachController {

    private final CoachService coachService;

    @Operation(summary = "Get name of coach by id")
    @GetMapping("/{userId}/name")
    public ResponseEntity<UserResponse> getCoachNameAndLastName(@PathVariable String userId) {
        try {
            UserResponse coachNameAndLastName = coachService.getCoachNameAndLastName(userId);
            System.out.println("Coach name and last name: " + coachNameAndLastName.getFirstName() + " " + coachNameAndLastName.getLastName());
            return ResponseEntity.ok(coachNameAndLastName);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    @Operation(summary = "Get clients by coach")
    @GetMapping("/{id}/clients")
    public ResponseEntity<List<ClientResponse>> getClientsByCoach(@PathVariable Long id) {
    	try {
    		List<ClientResponse> clients = coachService.getClientsByCoach(id);
    		return ResponseEntity.ok(clients);
    	}catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
