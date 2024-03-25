package com.fithub.services.training.test.configuration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = TrainingServiceCoreTestApplication.class)
public abstract class BasicTestConfiguration extends AbstractTestNGSpringContextTests {

}