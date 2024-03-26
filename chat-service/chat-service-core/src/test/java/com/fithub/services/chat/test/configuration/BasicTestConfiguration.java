package com.fithub.services.chat.test.configuration;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = ChatServiceCoreTestApplication.class)
public abstract class BasicTestConfiguration extends AbstractTestNGSpringContextTests {

}