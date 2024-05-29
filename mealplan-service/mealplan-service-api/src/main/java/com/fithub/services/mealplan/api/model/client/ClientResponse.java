package com.fithub.services.mealplan.api.model.client;

import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "The properties of client response object")
public class ClientResponse implements Serializable {
	
    private static final long serialVersionUID = 1L;

    private Long id;

    private String userId;
    
    private Long coachId;
    
    private String firstName;
    
    private String lastName;

}
