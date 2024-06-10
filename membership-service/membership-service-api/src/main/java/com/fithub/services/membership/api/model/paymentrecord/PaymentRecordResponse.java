package com.fithub.services.membership.api.model.paymentrecord;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fithub.services.membership.api.model.client.ClientResponse;

import lombok.Data;

@Data
public class PaymentRecordResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private LocalDateTime paymentDate;
    private Integer year;
    private String month;
    private Boolean paid;
    private ClientResponse client;

}