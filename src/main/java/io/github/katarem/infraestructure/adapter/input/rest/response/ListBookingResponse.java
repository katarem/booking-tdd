package io.github.katarem.infraestructure.adapter.input.rest.response;

import io.github.katarem.domain.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListBookingResponse {
    private Set<Booking> data;
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Set<SortingResponse> sort;
}
