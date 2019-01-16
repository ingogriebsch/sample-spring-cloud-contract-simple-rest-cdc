package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Size;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.Participant;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote.ParticipantClient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@Validated
public class ParticipantController {

    static final String PATH_PARTICIPANT = "/api/participant";

    @NonNull
    private final ParticipantClient participantClient;

    @GetMapping(path = "/api/participant", produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Participant> participant(@Size(min = 1) @RequestParam String name) {
        Participant p = participantClient.participant(name);
        return p != null ? ok(p) : notFound().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    void constraintViolationException() {
    }

}
