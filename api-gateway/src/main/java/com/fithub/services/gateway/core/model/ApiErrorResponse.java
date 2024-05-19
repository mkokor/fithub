package com.fithub.services.gateway.core.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ApiErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ApiError> errors;

}