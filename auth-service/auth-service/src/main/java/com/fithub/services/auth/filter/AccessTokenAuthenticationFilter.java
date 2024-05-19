package com.fithub.services.auth.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fithub.services.auth.api.response.ApiError;
import com.fithub.services.auth.api.response.ApiErrorResponse;
import com.fithub.services.auth.api.response.ApiErrorType;
import com.fithub.services.auth.core.utils.TokenHelper;
import com.fithub.services.auth.dao.model.UserEntity;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccessTokenAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenHelper tokenHelper;

    private String setAuthenticationErrorResponse(final String message) {
        try {
            final ApiErrorResponse apiErrorResponse = new ApiErrorResponse(List.of(new ApiError(ApiErrorType.AUTHORIZATION, message)));
            return new ObjectMapper().writeValueAsString(apiErrorResponse);
        } catch (Exception exception) {
            return null;
        }
    }

    private void sendErrorResponse(HttpServletResponse response, final String message) throws ServletException, IOException {
        final String apiErrorResponse = setAuthenticationErrorResponse(message);

        if (apiErrorResponse == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{ \"message\": \"Something went wrong.\" }");
            return;
        }

        response.setContentType(MediaType.APPLICATION_JSON);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(apiErrorResponse);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestPath = request.getRequestURI().toString();
        if (!requestPath.equals("/user/access-token/verification")) {
            chain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            sendErrorResponse(response, "The access token is not provided in valid format.");
            return;
        }

        String accessToken = authorizationHeader.substring(7);
        String username;
        try {
            username = tokenHelper.getUsernameFromJwt(accessToken);
        } catch (Exception exception) {
            sendErrorResponse(response, "The access token is not valid.");
            return;
        }

        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            sendErrorResponse(response, "The access token is not in valid format.");
            return;
        }

        UserDetails userDetails = new UserEntity();
        try {
            userDetails = this.userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException exception) {
            sendErrorResponse(response, "The access token owner could not be resolved.");
            return;
        }

        if (!tokenHelper.isJwtValid(accessToken, userDetails)) {
            sendErrorResponse(response, "The access token is not valid.");
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        chain.doFilter(request, response);
    }

}