package com.fithub.services.auth.test.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.fithub.services.auth.mapper" })
public class AuthServiceCoreTestApplicationConfiguration {

}