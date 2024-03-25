package com.fithub.services.auth.test.configuration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = AuthServiceCoreTestApplication.class)
public abstract class BasicTestConfiguration extends AbstractTestNGSpringContextTests {

}