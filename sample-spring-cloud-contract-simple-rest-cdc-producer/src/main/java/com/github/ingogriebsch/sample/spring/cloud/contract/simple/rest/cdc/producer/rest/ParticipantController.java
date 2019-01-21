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

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.model.Participant;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.service.ParticipantService;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
