package io.gihtub.katarem.infraestructure.mapper;

import io.gihtub.katarem.domain.criteria.BookingCriteria;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.model.BookingList;
import io.gihtub.katarem.infraestructure.adapter.input.rest.request.BookingRequest;
import io.gihtub.katarem.infraestructure.adapter.input.rest.request.ListBookingsRequest;
import io.gihtub.katarem.infraestructure.adapter.input.rest.response.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = SortingRestMapper.class)
public interface BookingRestMapper {

    GetBookingResponse toGetBookingResponse(Booking booking);

    Booking toDomain(BookingRequest request);

    BookingCriteria toCriteria(ListBookingsRequest request);

    CreateBookingResponse toCreateBookingResponse(Booking booking);

    ConfirmBookingResponse toConfirmBookingResponse(Booking booking);

    CancelBookingResponse toCancelBookingResponse(Booking booking);

    @Mapping(source = "bookings", target = "data")
    ListBookingResponse toListBookingResponse(BookingList bookingList);

}
