package com.fithub.services.config.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
public class FitHubConfigServerLauncher {

    public static void main(String[] args) {
        SpringApplication.run(FitHubConfigServerLauncher.class, args);
    }

}