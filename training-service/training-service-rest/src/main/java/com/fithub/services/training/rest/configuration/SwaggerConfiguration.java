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

    @Bean
    public GroupedOpenApi reservationApi() {
        return GroupedOpenApi.builder().group("reservation-api").packagesToScan("com.fithub.services.training.rest.reservation").build();
    }

    @Bean
    public GroupedOpenApi songApi() {
        return GroupedOpenApi.builder().group("song-api").packagesToScan("com.fithub.services.training.rest.song").build();
    }

}