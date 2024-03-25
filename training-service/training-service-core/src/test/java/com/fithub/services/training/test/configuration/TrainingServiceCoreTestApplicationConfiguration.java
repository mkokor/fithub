package com.fithub.services.training.test.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.fithub.services.training.mapper" })
public class TrainingServiceCoreTestApplicationConfiguration {

}