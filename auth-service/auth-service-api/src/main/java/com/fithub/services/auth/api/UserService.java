package com.fithub.services.auth.api;

import com.fithub.services.auth.api.exception.ApiException;
import com.fithub.services.auth.api.model.GenericResponse;
import com.fithub.services.auth.api.model.passwordresetcode.PasswordResetCodeRequest;
import com.fithub.services.auth.api.model.user.PasswordResetRequest;
import com.fithub.services.auth.api.model.user.UserSignInRequest;
import com.fithub.services.auth.api.model.user.UserSignInResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {

    UserSignInResponse signIn(UserSignInRequest userSignInRequest, HttpServletResponse httpResponse) throws Exception;

    GenericResponse sendPasswordResetCode(PasswordResetCodeRequest passwordResetCodeRequest) throws Exception;

    GenericResponse resetPassword(PasswordResetRequest passwordResetRequest) throws Exception;

    UserSignInResponse refreshAccessToken(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws ApiException;

    GenericResponse signOut(HttpServletRequest httpRequest, HttpServletResponse httpResponse);

}