package com.fithub.services.auth.api.response;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "The properties of an error response object")
public class ApiErrorResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<ApiError> errors;

}