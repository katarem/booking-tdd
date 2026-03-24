package io.github.katarem.infraestructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.temporal.ChronoUnit;

@Getter
@Setter
@ConfigurationProperties(prefix = "tolerance")
public class ToleranceConfigurationProperties {

    @Value("${booking-start-date.quantity:15}")
    private int quantity;
    @Value("${booking-start-date.unit:MINUTES}")
    private ChronoUnit unit;

}
