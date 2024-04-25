package com.fithub.services.systemevents.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan(basePackages = { "com.fithub.services.systemevents.services" })
public class SystemEventsServiceLauncher {

    public static void main(String[] args) {
        SpringApplication.run(SystemEventsServiceLauncher.class, args);
    }

}