/*
 * Copyright 2019 Ingo Griebsch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import java.util.Optional;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.model.Participant;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.service.ParticipantService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
public class ParticipantController {

    @NonNull
    private final ParticipantService participantService;

    @GetMapping(path = "/api/participants", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Participant> find(@Size(min = 1) @RequestParam String name) {
        Optional<Participant> participant = participantService.find(name);
        return participant.isPresent() ? ok(participant.get()) : notFound().build();
    }

    @PostMapping(path = "/api/participants", consumes = APPLICATION_JSON_UTF8_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Participant> insert(@RequestBody @Valid ParticipantInput input) {
        Participant participant = new Participant(input.getName());
        participant = participantService.insert(participant);
        return participant != null ? status(CREATED).body(participant) : status(CONFLICT).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    void constraintViolationException() {
    }

    @Data
    @NoArgsConstructor
    private static class ParticipantInput {

        @NonNull
        @NotBlank
        private String name;
    }

}
