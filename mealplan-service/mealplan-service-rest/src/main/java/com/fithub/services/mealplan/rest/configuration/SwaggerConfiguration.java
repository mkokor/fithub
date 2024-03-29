package com.fithub.services.mealplan.rest.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    public SwaggerConfiguration() {
        super();
    }

    @Bean
    public GroupedOpenApi clientApi() {
        return GroupedOpenApi.builder().group("client-api").packagesToScan("com.fithub.services.mealplan.rest.client").build();
    }

}