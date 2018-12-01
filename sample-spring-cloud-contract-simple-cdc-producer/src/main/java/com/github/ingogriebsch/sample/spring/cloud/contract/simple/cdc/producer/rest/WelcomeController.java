package com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.producer.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Value;

@RestController
public class WelcomeController {

    @GetMapping(path = "/api/welcome", produces = APPLICATION_JSON_UTF8_VALUE)
    public WelcomeMessage welcome(@RequestParam String name) {
        return new WelcomeMessage("Welcome " + name + "!");
    }

    @Value
    private static class WelcomeMessage {

        private String message;
    }
}
