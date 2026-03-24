package io.github.katarem.domain.policy;

import io.github.katarem.domain.policy.settings.TolerancePolicySettings;
import io.github.katarem.domain.exception.impl.booking.InvalidBookingStartDateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class TolerancePolicy {

    private final TolerancePolicySettings settings;

    public void validateBookingStartDateTolerance(ZonedDateTime startDate, ZonedDateTime now) {

        if(settings.unit().between(startDate, now) > settings.quantity()) {
            throw new InvalidBookingStartDateException();
        }

    }

}
