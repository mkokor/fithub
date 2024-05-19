package com.fithub.services.gateway.core.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String errorType;
    private String message;

}