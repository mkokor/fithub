package com.fithub.services.auth.rest.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    public SwaggerConfiguration() {
        super();
    }

    @Bean
    public GroupedOpenApi coachApi() {
        return GroupedOpenApi.builder().group("coach-api").packagesToScan("com.fithub.services.auth.rest.coach").build();
    }

    @Bean
    public GroupedOpenApi clientApi() {
        return GroupedOpenApi.builder().group("client-api").packagesToScan("com.fithub.services.auth.rest.client").build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder().group("user-api").packagesToScan("com.fithub.services.auth.rest.user").build();
    }

}