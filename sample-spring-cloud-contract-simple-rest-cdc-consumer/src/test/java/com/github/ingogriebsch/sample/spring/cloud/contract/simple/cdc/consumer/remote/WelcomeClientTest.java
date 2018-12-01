package com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.consumer.remote;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = { "com.github.ingogriebsch.samples:sample-spring-cloud-contract-simple-rest-cdc-producer:8080" })
public class WelcomeClientTest {

    @Autowired
    private WelcomeClient welcomeClient;

    @Test
    public void welcome_should_return_bad_request_if_query_param_name_is_missing() {
        Optional<WelcomeMessage> optional = welcomeClient.welcome(null);
        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void welcome_should_return_bad_request_if_query_param_name_is_empty() {
        Optional<WelcomeMessage> optional = welcomeClient.welcome(EMPTY);
        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void welcome_should_return_ok() {
        String name = "Peter";

        Optional<WelcomeMessage> optiona = welcomeClient.welcome(name);
        assertThat(optiona.isPresent()).isTrue();

        WelcomeMessage welcomeMessage = optiona.get();
        assertThat(welcomeMessage).isNotNull();

        String message = welcomeMessage.getMessage();
        assertThat(message).isNotNull().contains(name);
    }
}
