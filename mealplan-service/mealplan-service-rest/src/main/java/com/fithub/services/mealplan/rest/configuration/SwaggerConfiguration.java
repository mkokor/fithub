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
    public GroupedOpenApi mealPlanApi() {
        return GroupedOpenApi.builder().group("mealplan-api").packagesToScan("com.fithub.services.mealplan.rest.mealplan").build();
    }

    @Bean
    public GroupedOpenApi dailyMealPlanApi() {
        return GroupedOpenApi.builder().group("daily-mealplan-api").packagesToScan("com.fithub.services.mealplan.rest.dailymealplan")
                .build();
    }

}