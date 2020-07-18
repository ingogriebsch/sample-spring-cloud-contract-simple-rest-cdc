/*
 * Copyright 2019 Ingo Griebsch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote;

import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.util.Optional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ParticipantClient {

    @Value("${remote.host}")
    private String remoteHost;

    @NonNull
    private final RestTemplate restTemplate;

    public Optional<Participant> find(String name) {
        String url = remoteHost + "/api/participants";
        if (name != null) {
            url += "?name={name}";
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ACCEPT, APPLICATION_JSON_UTF8_VALUE);

        ResponseEntity<Participant> response;
        try {
            response = restTemplate.exchange(url, GET, new HttpEntity<>(httpHeaders), Participant.class, name);
        } catch (HttpStatusCodeException e) {
            if (NOT_FOUND.equals(e.getStatusCode())) {
                return empty();
            }
            throw e;
        }
        return ofNullable(response.getBody());
    }

    public Participant insert(@NonNull ParticipantInput input) {
        String url = remoteHost + "/api/participants";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ACCEPT, APPLICATION_JSON_UTF8_VALUE);
        httpHeaders.add(CONTENT_TYPE, APPLICATION_JSON_UTF8_VALUE);

        ResponseEntity<Participant> response;
        try {
            response = restTemplate.exchange(url, POST, new HttpEntity<>(input, httpHeaders), Participant.class);
        } catch (HttpStatusCodeException e) {
            if (CONFLICT.equals(e.getStatusCode())) {
                return null;
            }
            throw e;
        }
        return response.getBody();
    }

}
