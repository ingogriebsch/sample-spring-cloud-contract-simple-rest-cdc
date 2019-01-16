package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.rest;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.Application;

@ContextConfiguration(classes = { Application.class })
@RunWith(SpringRunner.class)
@WebMvcTest(ParticipantController.class)
public abstract class ParticipantBase {

    @Autowired
    private ParticipantController participantController;

    @Before
    public void before() {
        standaloneSetup(participantController);
    }

}
