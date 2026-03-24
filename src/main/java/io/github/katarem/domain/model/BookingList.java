package io.github.katarem.domain.model;

import io.github.katarem.domain.criteria.OrderCriteria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingList {
    private Set<Booking> bookings;
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Set<OrderCriteria> sort;
}
