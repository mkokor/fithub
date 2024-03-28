package com.fithub.services.training.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fithub.services.training" })
@EnableJpaRepositories("com.fithub.services.training.dao.repository")
@EntityScan(basePackages = { "com.fithub.services.training.dao.model" })
@EnableFeignClients("com.fithub.services.training.core.client")
@PropertySource("classpath:spotify-api.properties")
public class TrainingServiceLauncher {

    public static void main(String[] args) {
        SpringApplication.run(TrainingServiceLauncher.class, args);
    }

}