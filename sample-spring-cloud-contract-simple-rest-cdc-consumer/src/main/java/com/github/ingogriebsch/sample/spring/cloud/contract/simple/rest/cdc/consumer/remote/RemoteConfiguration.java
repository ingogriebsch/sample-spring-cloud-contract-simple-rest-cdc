package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.NonNull;

@Configuration
public class RemoteConfiguration {

    @Bean
    public RestTemplate restTemplate(@NonNull RestTemplateBuilder builder) {
        return builder.build();
    }
}
