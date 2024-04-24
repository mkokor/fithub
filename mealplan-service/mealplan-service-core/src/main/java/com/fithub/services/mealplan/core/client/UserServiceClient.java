package com.fithub.services.mealplan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fithub.services.mealplan.api.model.user.UserResponse;


public interface UserServiceClient {

    @GetMapping("/users/{userId}")
    UserResponse getUserById(@PathVariable("userId") String userId);
}
