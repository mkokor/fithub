package com.fithub.services.membership.api.model.membership;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a membership response object")
public class MembershipResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long clientId;
	
	private double amount;
	
	
	

	 
}
