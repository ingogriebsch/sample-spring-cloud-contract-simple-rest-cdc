package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ParticipantClient {

    @Value("${remote.host}")
    private String remoteHost;

    @NonNull
    private final RestTemplate restTemplate;

    public Participant find(String name) {
        String url = remoteHost + "/api/participants?name={name}";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ACCEPT, APPLICATION_JSON_UTF8_VALUE);

        ResponseEntity<Participant> response =
            restTemplate.exchange(url, GET, new HttpEntity<>(httpHeaders), Participant.class, name);
        return response.getBody();
    }
}
