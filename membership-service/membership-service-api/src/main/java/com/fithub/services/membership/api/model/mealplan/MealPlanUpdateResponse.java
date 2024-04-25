package com.fithub.services.membership.api.model.mealplan;

import java.io.Serializable;

import com.fithub.services.membership.api.model.client.ClientResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema
public class MealPlanUpdateResponse implements Serializable {

		
	 private static final long serialVersionUID = 1L;

	 private ClientResponse client;

	 private Boolean hasDebt;
}
