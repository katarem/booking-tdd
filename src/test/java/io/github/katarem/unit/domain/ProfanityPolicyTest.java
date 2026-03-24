package io.github.katarem.unit.domain;

import io.github.katarem.domain.policy.ProfanityPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class ProfanityPolicyTest {

    ProfanityPolicy profanityPolicy;

    @BeforeEach
    void setUp() {
        profanityPolicy = new ProfanityPolicy();
    }

    @Test
    void field_has_no_profanities_should_pass() {
        assertThatCode(() -> profanityPolicy.validateContent("field", "texto chill"))
                .doesNotThrowAnyException();
    }

    @Test
    void field_has_profanities_should_throw() {
        assertThatThrownBy(() -> profanityPolicy.validateContent("field", "mierda de texto"))
                .isInstanceOf(RuntimeException.class);
    }

}
