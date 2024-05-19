package com.fithub.services.gateway.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "routes")
@Data
public class RoutesConfig {

    private List<String> unauthorized;
    private AuthorizedRoutes authorized;

    @Data
    public static class AuthorizedRoutes {

        private List<String> any;
        private List<String> client;
        private List<String> coach;

    }

}