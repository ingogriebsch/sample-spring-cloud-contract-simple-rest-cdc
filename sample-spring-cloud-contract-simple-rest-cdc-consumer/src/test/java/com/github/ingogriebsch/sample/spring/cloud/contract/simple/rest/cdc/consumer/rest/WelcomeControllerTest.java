package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.rest;

import static com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.rest.WelcomeController.PATH_WELCOME;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.WelcomeClient;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.WelcomeMessage;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.rest.WelcomeController;

@RunWith(SpringRunner.class)
@WebMvcTest(WelcomeController.class)
public class WelcomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WelcomeClient welcomeClient;

    @Test
    public void welcome_should_return_not_found_if_query_param_name_missing() throws Exception {
        given(welcomeClient.welcome(null)).willReturn(empty());

        RequestBuilder builder = get(PATH_WELCOME).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isNotFound());
    }

    @Test
    public void welcome_should_return_ok() throws Exception {
        String name = "Peter";
        given(welcomeClient.welcome(name)).willReturn(Optional.of(new WelcomeMessage(name)));

        RequestBuilder builder = get(PATH_WELCOME + "?name={name}", name).accept(APPLICATION_JSON_UTF8);
        ResultActions result = mockMvc.perform(builder);

        assertThat(result).isNotNull();
        result.andExpect(status().isOk());
        result.andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }
}
