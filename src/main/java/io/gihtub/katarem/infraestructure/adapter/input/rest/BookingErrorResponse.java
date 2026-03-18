package io.gihtub.katarem.infraestructure.adapter.input.rest;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class BookingErrorResponse {
    private String code;
    private String message;
    private final Instant timestamp = Instant.now();
}
