package com.fithub.services.auth.rest.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fithub.services.auth.api.UserService;
import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.passwordresetcode.PasswordResetCodeRequest;
import com.fithub.services.auth.api.model.user.PasswordResetRequest;
import com.fithub.services.auth.api.model.user.UserSignInRequest;
import com.fithub.services.auth.api.model.user.UserSignInResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "user", description = "User API")
@RestController
@RequestMapping(value = "user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Sign in")
    @PostMapping(value = "/sign-in")
    public ResponseEntity<UserSignInResponse> signIn(@Valid @RequestBody UserSignInRequest userSignInRequest,
            HttpServletResponse httpResponse) throws Exception {
        return new ResponseEntity<>(userService.signIn(userSignInRequest, httpResponse), HttpStatus.OK);
    }

    @Operation(summary = "Send/resend password reset code")
    @PostMapping(value = "/{email}/password-reset-code")
    public ResponseEntity<GenericResponse> sendPasswordResetCode(@PathVariable final String email) throws Exception {
        PasswordResetCodeRequest passwordResetCodeRequest = new PasswordResetCodeRequest();
        passwordResetCodeRequest.setUserEmail(email);

        return new ResponseEntity<>(userService.sendPasswordResetCode(passwordResetCodeRequest), HttpStatus.OK);
    }

    @Operation(summary = "Reset password")
    @PostMapping(value = "/{email}/password-reset")
    public ResponseEntity<GenericResponse> resetPassword(@PathVariable final String email,
            @RequestBody final PasswordResetRequest passwordResetRequest) throws Exception {
        passwordResetRequest.setUserEmail(email);

        return new ResponseEntity<>(userService.resetPassword(passwordResetRequest), HttpStatus.OK);
    }

    @Operation(summary = "Refresh access and refresh token")
    @PostMapping(value = "/access-refresh")
    public ResponseEntity<UserSignInResponse> refreshAccessToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws Exception {
        return new ResponseEntity<>(userService.refreshAccessToken(httpRequest, httpResponse), HttpStatus.OK);
    }

}