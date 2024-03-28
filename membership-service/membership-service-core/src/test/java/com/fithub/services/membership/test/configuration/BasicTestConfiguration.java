package com.fithub.services.membership.test.configuration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = MembershipServiceCoreTestApplication.class)
public abstract class BasicTestConfiguration extends AbstractTestNGSpringContextTests {

}