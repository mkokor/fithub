package com.fithub.services.membership.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fithub.services.membership" })
@EnableJpaRepositories("com.fithub.services.membership.dao.repository")
@EntityScan(basePackages = { "com.fithub.services.membership.dao.model" })
public class MembershipServiceLauncher {

    public static void main(String[] args) {
        SpringApplication.run(MembershipServiceLauncher.class, args);
    }

}