package com.fithub.services.training.rest.configuration;

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
        return GroupedOpenApi.builder().group("appointment-api").packagesToScan("com.fithub.services.training.rest.appointment").build();
    }

}