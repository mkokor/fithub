package com.fithub.services.meal_plan.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fithub.services.meal_plan" })
@EnableJpaRepositories("com.fithub.services.meal_plan.dao.repository")
@EntityScan(basePackages = { "com.fithub.services.meal_plan.dao.model" })
public class MealPlanServiceLauncher {

    public static void main(String[] args) {
        SpringApplication.run(MealPlanServiceLauncher.class, args);
    }

}