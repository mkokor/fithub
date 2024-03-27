package com.fithub.services.chat.test.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.fithub.services.chat.mapper" })
public class ChatServiceCoreTestApplicationConfiguration {

}