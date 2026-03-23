package io.gihtub.katarem.infraestructure.mapper;

import io.gihtub.katarem.domain.criteria.BookingCriteria;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.infraestructure.adapter.input.rest.request.BookingRequest;
import io.gihtub.katarem.infraestructure.adapter.input.rest.request.ListBookingsRequest;
import io.gihtub.katarem.infraestructure.adapter.input.rest.response.CancelBookingResponse;
import io.gihtub.katarem.infraestructure.adapter.input.rest.response.ConfirmBookingResponse;
import io.gihtub.katarem.infraestructure.adapter.input.rest.response.CreateBookingResponse;
import io.gihtub.katarem.infraestructure.adapter.input.rest.response.GetBookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingRestMapper {

    GetBookingResponse toGetBookingResponse(Booking booking);

    Booking toDomain(BookingRequest request);

    BookingCriteria toCriteria(ListBookingsRequest request);

    CreateBookingResponse toCreateBookingResponse(Booking booking);

    ConfirmBookingResponse toConfirmBookingResponse(Booking booking);

    CancelBookingResponse toCancelBookingResponse(Booking booking);
}
