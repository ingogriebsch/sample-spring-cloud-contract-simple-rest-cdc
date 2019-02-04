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

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.Participant;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.ParticipantClient;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.ParticipantInput;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Validated
public class ParticipantController {

    static final String PATH_PARTICIPANTS = "/api/participants";

    @NonNull
    private final ParticipantClient participantClient;

    @GetMapping(path = PATH_PARTICIPANTS, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Participant> find(@Size(min = 1) @RequestParam String name) {
        Optional<Participant> participant = participantClient.find(name);
        return participant.isPresent() ? ok(participant.get()) : notFound().build();
    }

    @PostMapping(path = PATH_PARTICIPANTS, consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Participant> insert(@RequestBody @Valid ParticipantInput input) {
        Participant participant = participantClient.insert(input);
        return participant != null ? status(HttpStatus.CREATED).body(participant) : status(CONFLICT).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    void constraintViolationException() {
    }

}
