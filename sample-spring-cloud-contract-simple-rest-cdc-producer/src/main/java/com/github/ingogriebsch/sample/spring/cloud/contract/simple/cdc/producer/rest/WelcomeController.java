package com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.producer.rest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;

@RestController
@Validated
public class WelcomeController {

    @GetMapping(path = "/api/welcome", produces = APPLICATION_JSON_UTF8_VALUE)
    public WelcomeMessage welcome(@Size(min = 1) @RequestParam String name) {
        return new WelcomeMessage("Welcome " + name + "!");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    void constraintViolationException() {
    }

    @Value
    private static class WelcomeMessage {

        private String message;
    }
}
