package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.model;

import org.hibernate.validator.constraints.NotBlank;

import lombok.NonNull;
import lombok.Value;

@Value
public class Participant {

    @NonNull
    @NotBlank
    private String name;
}
