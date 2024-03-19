package com.fithub.services.chat.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fithub.services.chat" })
@EnableJpaRepositories("com.fithub.services.chat.dao.repository")
@EntityScan(basePackages = { "com.fithub.services.chat.dao.model" })
public class ChatServiceLauncher {

    public static void main(String[] args) {
        SpringApplication.run(ChatServiceLauncher.class, args);
    }

}