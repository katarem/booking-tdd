package io.gihtub.katarem.domain.policy;

import io.gihtub.katarem.domain.policy.settings.TolerancePolicySettings;
import io.gihtub.katarem.infraestructure.exception.impl.booking.InvalidBookingStartDateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
