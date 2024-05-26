package com.fithub.services.auth.rest.email;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.auth.api.EmailService;
import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.email.EmailSendMessageRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "email", description = "Email API")
@RestController
@RequestMapping(value = "email", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GenericResponse> sendFitHubEmailMessage(@Valid @RequestBody EmailSendMessageRequest emailSendMessageRequest)
            throws Exception {
        return new ResponseEntity<>(emailService.sendEmailMessage(emailSendMessageRequest), HttpStatus.OK);
    }

}