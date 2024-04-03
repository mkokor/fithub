package com.fithub.services.mealplan.rest.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.mealplan.api.ClientService;
import com.fithub.services.mealplan.api.model.mealplan.MealPlanResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
