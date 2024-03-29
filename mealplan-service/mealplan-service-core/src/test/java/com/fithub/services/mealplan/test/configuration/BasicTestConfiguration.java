package com.fithub.services.mealplan.test.configuration;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = MealPlanServiceCoreTestApplication.class)
public abstract class BasicTestConfiguration extends AbstractTestNGSpringContextTests {

}