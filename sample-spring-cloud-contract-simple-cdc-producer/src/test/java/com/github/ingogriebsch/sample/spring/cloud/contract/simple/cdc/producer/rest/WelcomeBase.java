package com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.producer.rest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WelcomeController.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public abstract class WelcomeBase {

    @Autowired
    private WelcomeController welcomeController;

    @Before
    public void before() {
        RestAssuredMockMvc.standaloneSetup(welcomeController);
    }

}
