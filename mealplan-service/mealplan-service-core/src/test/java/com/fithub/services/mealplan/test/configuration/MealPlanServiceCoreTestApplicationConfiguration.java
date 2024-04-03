package com.fithub.services.mealplan.test.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.fithub.services.mealplan.mapper" })
public class MealPlanServiceCoreTestApplicationConfiguration {

}