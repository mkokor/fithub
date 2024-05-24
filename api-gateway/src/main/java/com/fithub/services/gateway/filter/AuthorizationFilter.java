package com.fithub.services.gateway.filter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fithub.services.gateway.configuration.RoutesConfig;
import com.fithub.services.gateway.core.helper.AccessTokenHelper;
import com.fithub.services.gateway.core.model.ApiError;
import com.fithub.services.gateway.core.model.ApiErrorResponse;
import com.fithub.services.gateway.core.model.ApiErrorType;
import com.fithub.services.gateway.core.model.Role;
import com.fithub.services.gateway.core.model.UserAccessTokenVerificationResponse;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthorizationFilter implements GlobalFilter {

    private final AccessTokenHelper accessTokenHelper;
    private final RoutesConfig routesConfig;

    private static Boolean routeMatches(final String route, final List<String> patterns) {
        for (final String patternString : patterns) {
            Pattern pattern = Pattern.compile(patternString);
            Matcher matcher = pattern.matcher(route);

            if (matcher.matches()) {
                return true;
            }
        }

        return false;
    }

    private static ApiErrorResponse setErrorResponse(final ApiErrorType apiErrorType, final String message) {
        ApiError authorizationError = new ApiError();
        authorizationError.setErrorType(apiErrorType.getValue());
        authorizationError.setMessage(message);

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse();
        apiErrorResponse.setErrors(List.of(authorizationError));

        return apiErrorResponse;
    }

    private static Mono<Void> returnErrorResponse(final ApiErrorType apiErrorType, ServerWebExchange exchange,
            final HttpStatusCode statusCode, final String errorMessage) {
        ApiErrorResponse apiErrorResponse = setErrorResponse(apiErrorType, errorMessage);

        exchange.getResponse().setStatusCode(statusCode);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        try {
            DataBufferFactory dataBufferFactory = exchange.getResponse().bufferFactory();
            DataBuffer dataBuffer = dataBufferFactory.wrap(new ObjectMapper().writeValueAsBytes(apiErrorResponse));

            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        } catch (Exception exception) {
            exchange.getResponse().setStatusCode(HttpStatusCode.valueOf(500));
            return exchange.getResponse().setComplete();
        }
    }

    private Boolean clientIsAuthorized(final String userRole, final String requestPath) {
        return userRole.equals(Role.CLIENT.getValue()) && (routeMatches(requestPath, routesConfig.getAuthorized().getAny())
                || routeMatches(requestPath, routesConfig.getAuthorized().getClient()));
    }

    private Boolean coachIsAuthorized(final String userRole, final String requestPath) {
        return userRole.equals(Role.COACH.getValue()) && (routeMatches(requestPath, routesConfig.getAuthorized().getAny())
                || routeMatches(requestPath, routesConfig.getAuthorized().getCoach()));
    }

    private Mono<Void> processUserDetails(final String requestPath, final String accessToken, ServerWebExchange exchange,
            GatewayFilterChain chain) {
        try {
            UserAccessTokenVerificationResponse userAccessTokenVerificationResponse = accessTokenHelper.verifyAccessToken(accessToken);

            final String userRole = userAccessTokenVerificationResponse.getRole();
            if (!(clientIsAuthorized(userRole, requestPath) || coachIsAuthorized(userRole, requestPath))) {
                return returnErrorResponse(ApiErrorType.AUTHORIZATION, exchange, HttpStatusCode.valueOf(403),
                        "The user does not have the required role to access the resource.");
            }

            exchange.getRequest().mutate().header(HttpHeaders.AUTHORIZATION, userAccessTokenVerificationResponse.getUserUuid());
            return chain.filter(exchange);
        } catch (HttpClientErrorException exception) {
            return returnErrorResponse(ApiErrorType.AUTHORIZATION, exchange, HttpStatusCode.valueOf(401), "The access token is invalid.");
        } catch (Exception exception) {
            return returnErrorResponse(ApiErrorType.INTERNAL_SERVER, exchange, HttpStatusCode.valueOf(500), "Something went wrong.");
        }
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final String requestPath = exchange.getRequest().getPath().value();

        if (!(routeMatches(requestPath, routesConfig.getAuthorized().getAny())
                || routeMatches(requestPath, routesConfig.getAuthorized().getCoach())
                || routeMatches(requestPath, routesConfig.getAuthorized().getClient()))) {
            return chain.filter(exchange);
        }

        HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
        List<String> authorizationHeaderContent = requestHeaders.get("Authorization");

        if (authorizationHeaderContent == null) {
            return returnErrorResponse(ApiErrorType.AUTHORIZATION, exchange, HttpStatusCode.valueOf(401),
                    "The access token is not provided.");
        } else {
            String accessToken = authorizationHeaderContent.get(0);

            if (AccessTokenHelper.isValidBearerToken(accessToken)) {
                return processUserDetails(requestPath, accessToken, exchange, chain);
            } else {
                return returnErrorResponse(ApiErrorType.AUTHORIZATION, exchange, HttpStatusCode.valueOf(401),
                        "The access token is not provided in valid BEARER format.");
            }
        }

    }

}