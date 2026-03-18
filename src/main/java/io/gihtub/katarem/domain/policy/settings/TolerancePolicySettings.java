package io.gihtub.katarem.domain.policy.settings;

import java.time.temporal.ChronoUnit;

public record TolerancePolicySettings(
        int quantity,
        ChronoUnit unit
){}
