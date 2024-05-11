package com.fithub.services.auth.api;

import com.fithub.services.auth.api.exception.ApiException;
import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.emailconfirmationcode.EmailConfirmationCodeCreateOrUpdateRequest;

public interface EmailConfirmationCodeService {

    GenericResponse createOrUpdateEmailConfirmationCode(
            EmailConfirmationCodeCreateOrUpdateRequest emailConfirmationCodeCreateOrUpdateRequest) throws ApiException;

}