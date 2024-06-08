package com.fithub.services.mealplan.api.model.dailymealplan;

import java.io.Serializable;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DailyMealPlanUpdateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String breakfast;

    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String amSnack;

    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String lunch;

    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String dinner;

    @Size(min = 1, message = "A meal must contain at least 1 character.")
    @Size(max = 250, message = "A meal cannot contain more than 250 characters.")
    private String pmSnack;

}