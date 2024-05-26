package com.fithub.services.training.core.utils;

import feign.FeignException;

@FunctionalInterface
public interface FeignClientApiCallable<T> {

    T apply() throws FeignException;

}