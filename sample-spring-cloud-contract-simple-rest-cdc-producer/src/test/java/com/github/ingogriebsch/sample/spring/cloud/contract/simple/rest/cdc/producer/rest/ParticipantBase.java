package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.rest;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

import java.util.Optional;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.Application;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.model.Participant;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.service.ParticipantService;

@ContextConfiguration(classes = { Application.class })
@RunWith(SpringRunner.class)
@WebMvcTest(ParticipantController.class)
public abstract class ParticipantBase {

    @MockBean
    private ParticipantService participantService;

    @Autowired
    private ParticipantController participantController;

    @Before
    public void before() {
        given(participantService.find(anyString())).will(new Answer<Optional<Participant>>() {

            @Override
            public Optional<Participant> answer(InvocationOnMock invocation) throws Throwable {
                return of(new Participant((String) invocation.getArguments()[0]));
            }
        });
        given(participantService.find("__unknown__")).willReturn(empty());

        given(participantService.insert(any())).will(new Answer<Participant>() {

            @Override
            public Participant answer(InvocationOnMock invocation) throws Throwable {
                return (Participant) invocation.getArguments()[0];
            }
        });
        given(participantService.insert(new Participant("__already_known__"))).willReturn(null);

        standaloneSetup(participantController);
    }

}
