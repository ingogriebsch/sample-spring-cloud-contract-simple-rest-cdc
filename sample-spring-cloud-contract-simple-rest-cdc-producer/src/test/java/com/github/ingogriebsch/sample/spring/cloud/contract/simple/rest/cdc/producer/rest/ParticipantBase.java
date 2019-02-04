/*
 * Copyright 2019 Ingo Griebsch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
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
        given(participantService.find("__not_known__")).willReturn(empty());

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
