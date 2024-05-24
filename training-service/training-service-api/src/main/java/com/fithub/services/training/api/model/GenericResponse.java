package com.fithub.services.training.api.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class GenericResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

}