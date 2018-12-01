package com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.producer.rest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.producer.Application;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(WelcomeController.class)
@ContextConfiguration(classes = { Application.class })
public abstract class WelcomeBase {

    @Autowired
    private WelcomeController welcomeController;

    @Before
    public void before() {
        RestAssuredMockMvc.standaloneSetup(welcomeController);
    }

}
