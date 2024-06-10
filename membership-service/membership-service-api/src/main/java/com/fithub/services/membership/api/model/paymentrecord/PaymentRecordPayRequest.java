package com.fithub.services.membership.api.model.paymentrecord;

import java.io.Serializable;

import com.fithub.services.membership.dao.validation.annotation.MonthOfYear;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentRecordPayRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "The client UUID must be provided.")
    private String clientUuid;

    @MonthOfYear(message = "The provided month is not valid.")
    private String month;

    @Min(value = 2024, message = "The year must be at least 2024.")
    private Integer year;

}