package com.fithub.services.membership.api.model.paymentrecord;

import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of a payment record response object")
public class PaymentRecordResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long membershipId;
	
	private boolean paid;
	
	private String month;
	

}
