package com.fithub.services.auth.api;

import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.email.EmailSendMessageRequest;

public interface EmailService {

    GenericResponse sendEmailMessage(EmailSendMessageRequest emailSendMessageRequest) throws Exception;

}