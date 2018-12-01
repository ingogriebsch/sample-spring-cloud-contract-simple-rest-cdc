package com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.consumer.remote;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WelcomeClient {

    @Value("${remote.host}")
    private String remoteHost;

    @NonNull
    private final RestTemplate restTemplate;

    public Optional<WelcomeMessage> welcome(String name) {
        if (isEmpty(name)) {
            return empty();
        }

        String url = remoteHost + "/api/welcome?name={name}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ACCEPT, APPLICATION_JSON_UTF8_VALUE);
        return of(restTemplate.exchange(url, GET, new HttpEntity<>(httpHeaders), WelcomeMessage.class, name).getBody());
    }
}
