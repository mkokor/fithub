package com.fithub.services.auth.api;

import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.client.ClientEmailConfirmationRequest;
import com.fithub.services.auth.api.model.client.ClientSignUpRequest;

public interface ClientService {

    GenericResponse signUp(ClientSignUpRequest clientSignUpRequest) throws Exception;

    GenericResponse confimEmailAddress(ClientEmailConfirmationRequest clientEmailConfirmationRequest) throws Exception;

}