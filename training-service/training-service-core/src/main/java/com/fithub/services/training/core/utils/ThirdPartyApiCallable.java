package com.fithub.services.training.core.utils;

import com.fithub.services.training.api.exception.ThirdPartyApiException;

@FunctionalInterface
public interface ThirdPartyApiCallable<T> {

    T apply() throws ThirdPartyApiException;

}