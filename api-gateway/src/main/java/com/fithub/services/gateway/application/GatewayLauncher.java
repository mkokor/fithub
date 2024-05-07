package com.fithub.services.gateway.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.fithub.services.gateway.filter")
public class GatewayLauncher {

    public static void main(String[] args) {
        SpringApplication.run(GatewayLauncher.class, args);
    }

}