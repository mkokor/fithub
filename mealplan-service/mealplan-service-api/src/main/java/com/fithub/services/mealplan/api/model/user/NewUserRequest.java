package com.fithub.services.mealplan.api.model.user;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "The properties of user request object")
public class NewUserRequest implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Schema(description = "The first name of the user")
    @Size(min = 1, max = 15, message = "First name must be between 1 and 15 characters.")
    @NotBlank(message = "First name must be specified.")
    private String firstName;

    @Schema(description = "The last name of the user")
    @Size(min = 1, max = 15, message = "Last name must be between 1 and 15 characters.")
    @NotBlank(message = "Last name must be specified.")
    private String lastName;


}



