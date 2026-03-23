package io.gihtub.katarem.infraestructure.adapter.input.rest.response;

import io.gihtub.katarem.domain.model.Booking;
import lombok.Data;

import java.util.Set;

@Data
public class ListBookingResponse {
    private Set<Booking> data;
    private Integer page;
    private Integer size;
}
