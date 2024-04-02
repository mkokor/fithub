package com.fithub.services.eureka.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServiceRegistryLauncher {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServiceRegistryLauncher.class, args);
    }

}