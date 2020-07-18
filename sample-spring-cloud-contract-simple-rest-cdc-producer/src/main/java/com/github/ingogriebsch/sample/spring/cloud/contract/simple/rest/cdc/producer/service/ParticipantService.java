/*
 * Copyright 2019 Ingo Griebsch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.model.Participant;
import lombok.NonNull;
import org.springframework.stereotype.Component;

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
