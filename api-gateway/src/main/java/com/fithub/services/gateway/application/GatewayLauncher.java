package com.fithub.services.gateway.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

import com.fithub.services.gateway.configuration.RoutesConfig;

@SpringBootApplication
@ComponentScan("com.fithub.services.gateway")
@EnableConfigurationProperties(RoutesConfig.class)
public class GatewayLauncher {

    public static void main(String[] args) {
        SpringApplication.run(GatewayLauncher.class, args);
    }

}