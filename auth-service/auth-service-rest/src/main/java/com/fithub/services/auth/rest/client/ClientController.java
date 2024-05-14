package com.fithub.services.auth.rest.client;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.auth.api.ClientService;
import com.fithub.services.auth.api.EmailConfirmationCodeService;
import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.client.ClientEmailConfirmationRequest;
import com.fithub.services.auth.api.model.client.ClientSignUpRequest;
import com.fithub.services.auth.api.model.emailconfirmationcode.EmailConfirmationCodeCreateOrUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "client", description = "Client API")
@RestController
@RequestMapping(value = "client")
@AllArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final EmailConfirmationCodeService emailConfirmationCodeService;

    @Operation(summary = "Sign up a new client")
    @PostMapping(value = "/sign-up")
    public ResponseEntity<GenericResponse> signUp(@Valid @RequestBody ClientSignUpRequest clientSignUpRequest) throws Exception {
        return new ResponseEntity<>(clientService.signUp(clientSignUpRequest), HttpStatus.OK);
    }

    @Operation(summary = "Resend an email confirmation code")
    @PutMapping(value = "/{email}/email-confirmation-code/resend")
    public ResponseEntity<GenericResponse> resendEmailConfirmationCode(@PathVariable String email) throws Exception {
        EmailConfirmationCodeCreateOrUpdateRequest emailConfirmationCodeCreateOrUpdateRequest = new EmailConfirmationCodeCreateOrUpdateRequest();
        emailConfirmationCodeCreateOrUpdateRequest.setUserEmail(email);

        return new ResponseEntity<>(
                emailConfirmationCodeService.createOrUpdateEmailConfirmationCode(emailConfirmationCodeCreateOrUpdateRequest),
                HttpStatus.OK);
    }

    @Operation(summary = "Confirm an email address")
    @PutMapping(value = "/{email}/email-confirmation")
    public ResponseEntity<GenericResponse> confirmEmailAddress(@PathVariable String email,
            @Valid @RequestBody ClientEmailConfirmationRequest clientEmailConfirmationRequest) throws Exception {
        clientEmailConfirmationRequest.setEmail(email);

        return new ResponseEntity<>(clientService.confimEmailAddress(clientEmailConfirmationRequest), HttpStatus.OK);
    }

}