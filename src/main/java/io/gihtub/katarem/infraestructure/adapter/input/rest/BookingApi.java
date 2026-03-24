package io.gihtub.katarem.infraestructure.adapter.input.rest;

import io.gihtub.katarem.application.port.input.*;
import io.gihtub.katarem.domain.criteria.Direction;
import io.gihtub.katarem.domain.criteria.OrderCriteria;
import io.gihtub.katarem.domain.criteria.PageCriteria;
import io.gihtub.katarem.infraestructure.adapter.input.rest.request.CreateBookingRequest;
import io.gihtub.katarem.infraestructure.adapter.input.rest.request.ListBookingsRequest;
import io.gihtub.katarem.infraestructure.adapter.input.rest.response.*;
import io.gihtub.katarem.infraestructure.mapper.BookingRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingApi {

    private final GetBookingUseCase getBookingUseCase;
    private final CreateBookingUseCase createBookingUseCase;
    private final ConfirmBookingUseCase confirmBookingUseCase;
    private final CancelBookingUseCase cancelBookingUseCase;
    private final ListBookingUseCase listBookingUseCase;

    private final BookingRestMapper mapper;

    @GetMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public GetBookingResponse getBooking(@PathVariable UUID bookingId) {
        var booking = getBookingUseCase.getBooking(bookingId);
        return mapper.toGetBookingResponse(booking);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBookingResponse createBooking(@Valid @RequestBody CreateBookingRequest request) {
        var model = mapper.toDomain(request);
        var booking = createBookingUseCase.createBooking(model);
        return mapper.toCreateBookingResponse(booking);
    }

    @PatchMapping("/{bookingId}/confirm")
    @ResponseStatus(HttpStatus.OK)
    public ConfirmBookingResponse confirmBooking(@PathVariable UUID bookingId) {
        var booking = confirmBookingUseCase.confirmBooking(bookingId);
        return mapper.toConfirmBookingResponse(booking);
    }

    @PatchMapping("/{bookingId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public CancelBookingResponse cancelBooking(@PathVariable UUID bookingId) {
        var booking = cancelBookingUseCase.cancelBooking(bookingId);
        return mapper.toCancelBookingResponse(booking);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ListBookingResponse listBookings(@Valid @ModelAttribute ListBookingsRequest request, @PageableDefault Pageable pageable) {

        var pageCriteria = new PageCriteria();
        pageCriteria.setPage(pageable.getPageNumber());
        pageCriteria.setSize(pageable.getPageSize());

        var sort = pageable.getSort().stream()
                        .map(order ->
                                OrderCriteria.builder()
                                    .field(order.getProperty())
                                    .direction(Direction.valueOf(order.getDirection().name()))
                                    .build()
                        ).collect(Collectors.toSet());

        pageCriteria.setSort(sort);

        var bookingCriteria = mapper.toCriteria(request);

        var bookingList = listBookingUseCase.listBookings(bookingCriteria, pageCriteria);
        return mapper.toListBookingResponse(bookingList);
    }

}
