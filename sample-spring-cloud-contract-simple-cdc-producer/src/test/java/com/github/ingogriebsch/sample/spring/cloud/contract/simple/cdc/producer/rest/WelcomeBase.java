package com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.producer.rest;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;

import org.junit.Before;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = WelcomeController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class WelcomeBase {

    @Before
    public void setup() {
        standaloneSetup(new WelcomeController());
    }

}
