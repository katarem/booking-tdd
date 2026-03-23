package io.gihtub.katarem.infraestructure.adapter.input.rest;

import io.gihtub.katarem.application.port.input.*;
import io.gihtub.katarem.infraestructure.adapter.input.rest.request.BookingRequest;
import io.gihtub.katarem.infraestructure.adapter.input.rest.request.ListBookingsRequest;
import io.gihtub.katarem.infraestructure.adapter.input.rest.response.*;
import io.gihtub.katarem.infraestructure.mapper.BookingRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
    public CreateBookingResponse createBooking(@Valid @RequestBody BookingRequest request) {
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
    public ListBookingResponse listBookings(@Valid ListBookingsRequest request) {
        throw new UnsupportedOperationException();
    }

}
