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
    public GroupedOpenApi membershipApi() {
        return GroupedOpenApi.builder().group("membership-api").packagesToScan("com.fithub.services.membership.rest.membership").build();
    }

    @Bean
    public GroupedOpenApi paymentRecordApi() {
        return GroupedOpenApi.builder().group("payment-record-api").packagesToScan("com.fithub.services.membership.rest.paymentrecord")
                .build();
    }

}