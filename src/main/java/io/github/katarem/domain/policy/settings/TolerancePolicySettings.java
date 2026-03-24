package io.github.katarem.domain.policy.settings;

import java.time.temporal.ChronoUnit;

public record TolerancePolicySettings(
        int quantity,
        ChronoUnit unit
){}
