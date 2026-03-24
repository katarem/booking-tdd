package io.github.katarem.infraestructure.mapper;

import io.github.katarem.domain.criteria.BookingCriteria;
import io.github.katarem.domain.model.Booking;
import io.github.katarem.domain.model.BookingList;
import io.github.katarem.infraestructure.adapter.input.rest.request.CreateBookingRequest;
import io.github.katarem.infraestructure.adapter.input.rest.request.ListBookingsRequest;
import io.gihtub.katarem.infraestructure.adapter.input.rest.response.*;
import io.github.katarem.infraestructure.adapter.input.rest.response.CancelBookingResponse;
import io.github.katarem.infraestructure.adapter.input.rest.response.ConfirmBookingResponse;
import io.github.katarem.infraestructure.adapter.input.rest.response.CreateBookingResponse;
import io.github.katarem.infraestructure.adapter.input.rest.response.GetBookingResponse;
import io.github.katarem.infraestructure.adapter.input.rest.response.ListBookingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = SortingRestMapper.class)
public interface BookingRestMapper {

    GetBookingResponse toGetBookingResponse(Booking booking);

    @Mapping(target = "status", expression = "java(io.github.katarem.domain.model.BookingStatus.PENDING)")
    Booking toDomain(CreateBookingRequest request);

    BookingCriteria toCriteria(ListBookingsRequest request);

    CreateBookingResponse toCreateBookingResponse(Booking booking);

    ConfirmBookingResponse toConfirmBookingResponse(Booking booking);

    CancelBookingResponse toCancelBookingResponse(Booking booking);

    @Mapping(source = "bookings", target = "data")
    ListBookingResponse toListBookingResponse(BookingList bookingList);

}
