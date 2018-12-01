package com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.consumer.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.consumer.remote.WelcomeClient;
import com.github.ingogriebsch.sample.spring.cloud.contract.simple.cdc.consumer.remote.WelcomeMessage;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class WelcomeController {

    static final String PATH_WELCOME = "/api/welcome";

    @NonNull
    private final WelcomeClient welcomeClient;

    @GetMapping(path = PATH_WELCOME, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<WelcomeMessage> welcome(@RequestParam(required = false) String name) {
        Optional<WelcomeMessage> welcomeMessage = welcomeClient.welcome(name);
        return welcomeMessage.isPresent() ? ok(welcomeMessage.get()) : notFound().build();
    }

}
