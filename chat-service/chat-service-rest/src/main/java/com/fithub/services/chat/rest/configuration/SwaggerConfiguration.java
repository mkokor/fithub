package com.fithub.services.chat.rest.configuration;

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
        return GroupedOpenApi.builder().group("message-api").packagesToScan("com.fithub.services.chat.rest.message").build();
    }

    @Bean
    public GroupedOpenApi chatroomApi() {
        return GroupedOpenApi.builder().group("chatroom-api").packagesToScan("com.fithub.services.chat.rest.chatroom").build();
    }

}