package com.github.ingogriebsch.sample.spring.cloud.contract.simple.rest.cdc.consumer.remote;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureStubRunner(ids = { "com.github.ingogriebsch.samples:sample-spring-cloud-contract-simple-rest-cdc-producer:8080" })
public class ParticipantClientTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private ParticipantClient participantClient;

    @Test
    public void participant_should_throw_exception_containing_http_status_400_if_param_name_is_missing() {
        Matcher<HttpStatusCodeException> matchers =
            allOf(instanceOf(HttpClientErrorException.class), HttpStatusCodeMatcher.hasHttpStatus(BAD_REQUEST));
        expectedException.expect(matchers);
        participantClient.participant(null);
    }

    @Test
    public void participant_should_throw_exception_containing_http_status_400_if_param_name_is_empty() {
        Matcher<HttpStatusCodeException> matchers =
            allOf(instanceOf(HttpClientErrorException.class), HttpStatusCodeMatcher.hasHttpStatus(BAD_REQUEST));
        expectedException.expect(matchers);
        participantClient.participant(EMPTY);
    }

    @Test
    public void participant_should_return_ok_if_participant_is_known() {
        String name = "Peter";
        Participant participant = participantClient.participant(name);
        assertThat(participant).isNotNull();
        assertThat(participant.getName()).isNotNull().isEqualTo(name);
    }

    @RequiredArgsConstructor
    private static class HttpStatusCodeMatcher extends BaseMatcher<HttpStatusCodeException> {

        @NonNull
        private final HttpStatus httpStatus;

        @Override
        public void describeTo(Description description) {
            description.appendText("has http status ").appendValue(httpStatus);
        }

        @Override
        public boolean matches(Object item) {
            return item != null && HttpStatusCodeException.class.isAssignableFrom(item.getClass())
                && httpStatus.equals(((HttpStatusCodeException) item).getStatusCode());
        }

        @Factory
        public static Matcher<HttpStatusCodeException> hasHttpStatus(HttpStatus operand) {
            return new HttpStatusCodeMatcher(operand);
        }
    }
}
