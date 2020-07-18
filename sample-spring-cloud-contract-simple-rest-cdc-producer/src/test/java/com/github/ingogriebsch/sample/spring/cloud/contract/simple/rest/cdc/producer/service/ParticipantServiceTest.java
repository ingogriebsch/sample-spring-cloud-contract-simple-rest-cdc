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

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.producer.model.Participant;
import org.junit.Test;

public class ParticipantServiceTest {

    private ParticipantService participantService = new ParticipantService();

    @Test(expected = NullPointerException.class)
    public void find_should_throw_exception_if_name_is_null() {
        participantService.find(null);
    }

    @Test
    public void find_should_return_empty_optional_if_participant_is_not_known() {
        Optional<Participant> optional = participantService.find(randomAlphabetic(5));
        assertThat(optional).isNotNull();
        assertThat(optional.isPresent()).isFalse();
    }

    @Test
    public void find_should_return_filled_optional_if_participant_is_known() {
        Participant participant = new Participant(randomAlphabetic(5));
        participantService.insert(participant);

        Optional<Participant> optional = participantService.find(participant.getName());
        assertThat(optional).isNotNull();
        assertThat(optional.isPresent()).isTrue();
        assertThat(optional.get()).isEqualTo(participant);
    }

    @Test(expected = NullPointerException.class)
    public void insert_should_throw_exception_if_input_is_null() {
        participantService.insert(null);
    }

    @Test
    public void insert_should_return_participant_if_participant_is_not_known() {
        Participant participant = new Participant(randomAlphabetic(5));
        assertThat(participantService.insert(participant)).isNotNull().isEqualTo(participant);
    }

    @Test
    public void insert_should_return_null_if_participant_is_known() {
        Participant participant = new Participant(randomAlphabetic(5));
        assertThat(participantService.insert(participant)).isNotNull().isEqualTo(participant);
        assertThat(participantService.insert(participant)).isNull();
    }
}
