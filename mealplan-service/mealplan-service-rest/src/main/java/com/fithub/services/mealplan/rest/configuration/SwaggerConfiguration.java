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
    
    @Bean
    public GroupedOpenApi coachApi() {
        return GroupedOpenApi.builder().group("coach-api").packagesToScan("com.fithub.services.mealplan.rest.coach").build();
    }
    
    @Bean
    public GroupedOpenApi mealPlanApi() {
        return GroupedOpenApi.builder().group("mealplan-api").packagesToScan("com.fithub.services.mealplan.rest.mealplan").build();
    }

}