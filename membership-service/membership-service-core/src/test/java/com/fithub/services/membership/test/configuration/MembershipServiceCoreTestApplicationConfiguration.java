package com.fithub.services.membership.test.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.fithub.services.membership.mapper" })
public class MembershipServiceCoreTestApplicationConfiguration {

}
