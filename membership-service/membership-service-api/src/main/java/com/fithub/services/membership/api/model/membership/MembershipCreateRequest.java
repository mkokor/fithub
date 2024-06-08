package com.fithub.services.membership.api.model.membership;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MembershipCreateRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "The monthly amount must be specified.")
    @Min(value = 0, message = "The monthly amount must not be negative.")
    private Double monthlyAmount;

    @NotNull(message = "The client UUID must be specified.")
    private String clientUuid;

}