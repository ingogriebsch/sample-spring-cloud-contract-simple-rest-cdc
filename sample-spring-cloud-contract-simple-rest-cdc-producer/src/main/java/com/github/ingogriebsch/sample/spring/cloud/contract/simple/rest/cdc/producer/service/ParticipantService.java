package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.model.Participant;

import lombok.NonNull;

@Component
public class ParticipantService {

    private final Set<Participant> participants = new HashSet<>();

    public Optional<Participant> find(@NonNull String name) {
        return get(name, participants);
    }

    public Participant insert(@NonNull Participant participant) {
        if (get(participant.getName(), participants).isPresent()) {
            return null;
        }

        participants.add(participant);
        return participant;
    }

    private static Optional<Participant> get(String name, Set<Participant> participants) {
        return participants.stream().filter(p -> name.equalsIgnoreCase(p.getName())).findAny();
    }

}
