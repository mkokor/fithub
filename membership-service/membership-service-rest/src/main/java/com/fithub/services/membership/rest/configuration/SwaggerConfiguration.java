package com.fithub.services.membership.rest.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    public SwaggerConfiguration() {
        super();
    }

    @Bean
    public GroupedOpenApi appointmentApi() {
        return GroupedOpenApi.builder().group("appointment-api").packagesToScan("com.fithub.services.membership.rest.appointment").build();
    }
    
    @Bean
    public GroupedOpenApi membershipApi() {
        return GroupedOpenApi.builder().group("membership-api").packagesToScan("com.fithub.services.membership.rest.membership").build();
    }
    
    @Bean
    public GroupedOpenApi coachApi() {
        return GroupedOpenApi.builder().group("coach-api").packagesToScan("com.fithub.services.membership.rest.coach").build();
    }

}