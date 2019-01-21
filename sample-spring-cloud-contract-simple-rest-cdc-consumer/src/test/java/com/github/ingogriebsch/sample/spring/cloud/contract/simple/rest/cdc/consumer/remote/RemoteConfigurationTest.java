package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

public class RemoteConfigurationTest {

    @Test(expected = NullPointerException.class)
    public void restTemplate_should_throw_exception_if_builder_is_null() {
        new RemoteConfiguration().restTemplate(null);
    }

    @Test
    public void restTemplate_should_return_template_if_builder_is_given() {
        RestTemplateBuilder builder = new RestTemplateBuilder();
        RestTemplate restTemplate = new RemoteConfiguration().restTemplate(builder);
        assertThat(restTemplate).isNotNull();
    }
}
