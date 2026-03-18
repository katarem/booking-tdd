package io.gihtub.katarem.infraestructure.adapter.input.rest;

import io.gihtub.katarem.application.port.input.GetBookingUseCase;
import io.gihtub.katarem.infraestructure.mapper.BookingRestMapper;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookings")
public class BookingApi {

    private final GetBookingUseCase getBookingUseCase;
    private final BookingRestMapper mapper;

    @GetMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.OK)
    public GetBookingResponse getBooking(@PathVariable UUID bookingId) {
        var booking = getBookingUseCase.getBooking(bookingId);
        return mapper.toGetBookingResponse(booking);
    }

}
