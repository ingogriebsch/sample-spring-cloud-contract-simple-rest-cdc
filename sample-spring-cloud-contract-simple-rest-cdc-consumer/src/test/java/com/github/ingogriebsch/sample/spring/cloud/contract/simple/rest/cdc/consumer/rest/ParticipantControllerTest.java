/*
 * Copyright 2019 Ingo Griebsch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.rest;

import static java.util.Optional.empty;
import static java.util.Optional.of;

import static com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.rest.ParticipantController.PATH_PARTICIPANTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.Participant;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.ParticipantClient;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.ParticipantInput;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

@RunWith(SpringRunner.class)
@WebMvcTest(ParticipantController.class)
public class ParticipantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipantClient participantClient;

    @Test
    public void find_should_return_bad_request_if_query_param_name_is_missing() throws Exception {
        given(participantClient.find(any())).willReturn(empty());

        RequestBuilder builder = get(PATH_PARTICIPANTS).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void find_should_return_not_found_if_participant_is_not_known() throws Exception {
        String name = "Unknown";
        given(participantClient.find(name)).willReturn(empty());

        RequestBuilder builder = get(PATH_PARTICIPANTS + "?name={name}", name).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isNotFound());
    }

    @Test
    public void find_should_return_ok_if_participant_is_known() throws Exception {
        String name = "Peter";
        given(participantClient.find(name)).willReturn(of(new Participant(name)));

        RequestBuilder builder = get(PATH_PARTICIPANTS + "?name={name}", name).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void insert_should_return_bad_request_if_input_name_is_missing() throws Exception {
        RequestBuilder builder =
            post(PATH_PARTICIPANTS).content("{}").contentType(APPLICATION_JSON_UTF8).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void insert_should_return_bad_request_if_input_is_legal() throws Exception {
        String name = "Alex";
        given(participantClient.insert(new ParticipantInput(name))).willReturn(new Participant(name));

        RequestBuilder builder = post(PATH_PARTICIPANTS).content("{\"name\": \"" + name + "\"}")
            .contentType(APPLICATION_JSON_UTF8).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isCreated());
        result.andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void insert_should_return_conflict_if_participant_is_already_known() throws Exception {
        String name = "Alex";
        given(participantClient.insert(new ParticipantInput(name))).willReturn(null);

        RequestBuilder builder = post(PATH_PARTICIPANTS).content("{\"name\": \"" + name + "\"}")
            .contentType(APPLICATION_JSON_UTF8).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isConflict());
    }
}
