package com.fithub.services.training.core.utils;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.fithub.services.training.api.exception.ApiException;

import feign.FeignException;

@Component
public class FeignClientHelper {

    public <T> T callApi(FeignClientApiCallable<T> supplier) throws ApiException {
        T responseObject = null;

        try {
            responseObject = supplier.apply();
        } catch (FeignException feignException) {
            throw new ApiException(feignException.getMessage(), HttpStatus.valueOf(feignException.status()));
        }

        return responseObject;
    }

}