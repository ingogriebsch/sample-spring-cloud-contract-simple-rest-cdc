package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.rest;

import static com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.rest.ParticipantController.PATH_PARTICIPANT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.Participant;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.ParticipantClient;

@RunWith(SpringRunner.class)
@WebMvcTest(ParticipantController.class)
public class ParticipantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipantClient participantClient;

    @Test
    public void participant_should_return_bad_request_if_query_param_name_is_missing() throws Exception {
        given(participantClient.participant(any())).willReturn(null);

        RequestBuilder builder = get(PATH_PARTICIPANT).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void participant_should_return_not_found_if_participant_is_not_known() throws Exception {
        String name = "Unknown";
        given(participantClient.participant(name)).willReturn(null);

        RequestBuilder builder = get(PATH_PARTICIPANT + "?name={name}", name).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isNotFound());
    }

    @Test
    public void participant_should_return_ok_if_participant_is_known() throws Exception {
        String name = "Peter";
        given(participantClient.participant(name)).willReturn(new Participant(name));

        RequestBuilder builder = get(PATH_PARTICIPANT + "?name={name}", name).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }
}
