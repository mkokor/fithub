package com.fithub.services.mealplan.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.fithub.services.mealplan.configuration.RibbonConfig;

@SpringBootApplication
@ComponentScan(basePackages = { "com.fithub.services.mealplan" })
@EnableJpaRepositories("com.fithub.services.mealplan.dao.repository")
@EntityScan(basePackages = { "com.fithub.services.mealplan.dao.model" })
@EnableFeignClients("com.fithub.services.mealplan.core.client")
//@RibbonClient(name = "fithub-auth-service", configuration = RibbonConfig.class)
public class MealPlanServiceLauncher {

    public static void main(String[] args) {
        SpringApplication.run(MealPlanServiceLauncher.class, args);
    }

}