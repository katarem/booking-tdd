package io.gihtub.katarem.unit.domain.policy;

import io.gihtub.katarem.domain.policy.TolerancePolicy;
import io.gihtub.katarem.domain.policy.settings.TolerancePolicySettings;
import io.gihtub.katarem.infraestructure.exception.impl.booking.InvalidBookingStartDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TolerancePolicyTest {

    final Clock clock = Clock.fixed(
            Instant.parse("2026-03-18T00:00:00Z"),
            ZoneOffset.UTC
    );

    TolerancePolicy policy;

    @BeforeEach
    void setUp() {
        var toleranceSettings = new TolerancePolicySettings(15, ChronoUnit.MINUTES);
        policy = new TolerancePolicy(toleranceSettings);
    }

    @Test
    @DisplayName("Booking start date does not meet tolerance requirements")
    void start_date_does_not_meet_tolerance_requirements() {

        // given
        ZonedDateTime now = ZonedDateTime.now(clock).withHour(9);
        ZonedDateTime startDate = now.minusMinutes(16);

        assertThatThrownBy(() -> policy.validateBookingStartDateTolerance(startDate, now))
                .isInstanceOf(InvalidBookingStartDateException.class);

    }

    @Test
    @DisplayName("Booking start date does not meet tolerance requirements")
    void start_date_meets_tolerance_requirements() {

        // given
        ZonedDateTime now = ZonedDateTime.now(clock).withHour(9);
        ZonedDateTime startDate = now.minusMinutes(10);

        assertThatCode(() -> policy.validateBookingStartDateTolerance(startDate, now))
                .doesNotThrowAnyException();
    }


}
