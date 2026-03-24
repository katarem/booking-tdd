package io.gihtub.katarem.web;

import io.gihtub.katarem.application.port.input.CancelBookingUseCase;
import io.gihtub.katarem.application.port.input.ConfirmBookingUseCase;
import io.gihtub.katarem.application.port.input.CreateBookingUseCase;
import io.gihtub.katarem.application.port.input.GetBookingUseCase;
import io.gihtub.katarem.application.port.input.ListBookingUseCase;
import io.gihtub.katarem.domain.criteria.BookingCriteria;
import io.gihtub.katarem.domain.criteria.Direction;
import io.gihtub.katarem.domain.criteria.OrderCriteria;
import io.gihtub.katarem.domain.criteria.PageCriteria;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.model.BookingList;
import io.gihtub.katarem.domain.model.BookingStatus;
import io.gihtub.katarem.infraestructure.adapter.input.rest.BookingApi;
import io.gihtub.katarem.infraestructure.adapter.input.rest.request.CreateBookingRequest;
import io.gihtub.katarem.infraestructure.exception.BookingExceptionHandler;
import io.gihtub.katarem.infraestructure.exception.impl.booking.BookingCancellationException;
import io.gihtub.katarem.infraestructure.exception.impl.booking.BookingConfirmationException;
import io.gihtub.katarem.infraestructure.exception.impl.booking.BookingNotFoundException;
import io.gihtub.katarem.infraestructure.mapper.BookingRestMapperImpl;
import io.gihtub.katarem.infraestructure.mapper.SortingRestMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureWebMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingApi.class)
@Import({BookingRestMapperImpl.class, SortingRestMapperImpl.class, BookingExceptionHandler.class})
@AutoConfigureWebMvc
public class BookingApiTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    GetBookingUseCase getBookingUseCase;

    @MockitoBean
    CreateBookingUseCase createBookingUseCase;

    @MockitoBean
    ConfirmBookingUseCase confirmBookingUseCase;

    @MockitoBean
    CancelBookingUseCase cancelBookingUseCase;

    @MockitoBean
    ListBookingUseCase listBookingUseCase;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Get Booking returns OK")
    void get_booking_returns_ok() throws Exception {

        // given
        UUID bookingId = UUID.randomUUID();
        Booking returned = Booking.builder()
                .id(bookingId)
                .build();

        String expected = String.format(
                """
                {
                    "id": "%s"
                }
                """, bookingId
        );

        // when
        when(getBookingUseCase.getBooking(bookingId))
                .thenReturn(returned);

        // then
        mockMvc.perform(get("/api/bookings/" + bookingId))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("Get Booking returns Not Found")
    void get_booking_returns_not_found() throws Exception {

        // given
        UUID bookingId = UUID.randomUUID();

        String expected = String.format("Booking with id %s was not found", bookingId);

        // when
        when(getBookingUseCase.getBooking(bookingId))
                .thenThrow(new BookingNotFoundException(bookingId));

        // then
        mockMvc.perform(get("/api/bookings/" + bookingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expected))
                .andExpect(jsonPath("$.code").value("BOOKING_NOT_FOUND"))
                .andExpect(jsonPath("$.timestamp").exists());

    }

    @Test
    @DisplayName("Create Booking should return OK")
    void create_booking_returns_ok() throws Exception {

        // given
        Booking booking = Booking.builder()
                .title("Nice title")
                .description("Epic description")
                .startDateTime(ZonedDateTime.now())
                .endDateTime(ZonedDateTime.now())
                .roomId(1)
                .employeeId(1)
                .status(BookingStatus.PENDING)
                .attendeesCount(1)
                .build();


        // when
        when(createBookingUseCase.createBooking(any()))
                .thenReturn(booking);

        // then
        mockMvc.perform(
                        post("/api/bookings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(booking))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Create Booking throws Internal Error")
    void create_booking_throws_internal_error() throws Exception {

        // given
        Booking booking = Booking.builder()
                .title("Nice title")
                .description("Epic description")
                .startDateTime(ZonedDateTime.now())
                .endDateTime(ZonedDateTime.now())
                .roomId(1)
                .employeeId(1)
                .status(BookingStatus.PENDING)
                .attendeesCount(1)
                .build();


        // when
        when(createBookingUseCase.createBooking(any()))
                .thenThrow(RuntimeException.class);

        // then
        mockMvc.perform(
                post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking))
                    )
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Create booking Throws Validation Error")
    void create_booking_throws_validation_error() throws Exception {

        // given
        CreateBookingRequest request = CreateBookingRequest.builder()
                .title("")
                .description("Epic description")
                .startDateTime(ZonedDateTime.now())
                .endDateTime(ZonedDateTime.now())
                .roomId(1)
                .employeeId(1)
                .attendeesCount(1)
                .build();

        // then
        mockMvc.perform(
                    post("/api/bookings")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isBadRequest());

    }

    @Test
    void confirm_booking_goes_ok() throws Exception {

        // given
        UUID bookingId = UUID.randomUUID();
        
        // when
        when(confirmBookingUseCase.confirmBooking(bookingId))
                .thenReturn(Booking.builder().id(bookingId).status(BookingStatus.CONFIRMED).build());

        // then
        mockMvc.perform(
                    patch("/api/bookings/" + bookingId + "/confirm")
                )
                .andExpect(status().isOk());
    }

    @Test
    void confirm_booking_has_wrong_state() throws Exception {

        // given
        UUID bookingId = UUID.randomUUID();

        // when
        when(confirmBookingUseCase.confirmBooking(bookingId))
                .thenThrow(BookingConfirmationException.class);

        // then
        mockMvc.perform(
                        patch("/api/bookings/" + bookingId + "/confirm")
                )
                .andExpect(status().isConflict());
    }

    @Test
    void cancel_booking_goes_ok() throws Exception {
        // given
        UUID bookingId = UUID.randomUUID();

        // when
        when(cancelBookingUseCase.cancelBooking(bookingId))
                .thenReturn(Booking.builder().id(bookingId).status(BookingStatus.CANCELLED).build());

        // then
        mockMvc.perform(
                        patch("/api/bookings/" + bookingId + "/cancel")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookingId.toString()))
                .andExpect(jsonPath("$.status").value(BookingStatus.CANCELLED.name()));
    }

    @Test
    void cancel_booking_has_wrong_state() throws Exception {
        // given
        UUID bookingId = UUID.randomUUID();

        // when
        when(cancelBookingUseCase.cancelBooking(bookingId))
                .thenThrow(BookingCancellationException.class);

        // then
        mockMvc.perform(
                        patch("/api/bookings/" + bookingId + "/cancel")
                )
                .andExpect(status().isConflict());
    }

    @Test
    void get_bookings_no_filters_goes_ok() throws Exception {

        // given
        var page = new PageCriteria();
        page.setSize(10);
        page.setPage(0);
        page.setSort(Collections.emptySet());

        // when
        when(listBookingUseCase.listBookings(new BookingCriteria(), page))
                .thenReturn(
                        BookingList.builder()
                                .bookings(Collections.singleton(Booking.builder().build()))
                                .page(page.getPage())
                                .size(page.getSize())
                                .build()
                );
        // then
        mockMvc.perform(
                        get("/api/bookings")
                                .queryParam("page", "0")
                                .queryParam("size", "10")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isNotEmpty());
    }

    @Test
    void get_bookings_with_filters_returns_empty() throws Exception {

        // given
        var page = new PageCriteria();
        page.setSize(10);
        page.setPage(0);
        page.setSort(Collections.emptySet());

        // when
        when(listBookingUseCase.listBookings(new BookingCriteria(), page))
                .thenReturn(
                        BookingList.builder()
                                .bookings(Collections.emptySet())
                                .page(page.getPage())
                                .size(page.getSize())
                                .build()
                );
        // then
        mockMvc.perform(
                        get("/api/bookings")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    void get_bookings_no_filters_invalid_paging() throws Exception {

        var page = new PageCriteria();
        page.setSize(10);
        page.setPage(0);
        page.setSort(Collections.emptySet());

        //when
        when(listBookingUseCase.listBookings(new BookingCriteria(), page))
                .thenReturn(
                        BookingList.builder()
                                .bookings(Collections.emptySet())
                                .page(page.getPage())
                                .size(page.getSize())
                                .sort(page.getSort())
                                .build()
                );

        // then
        mockMvc.perform(
                        get("/api/bookings")
                                .queryParam("page", "-1")
                                .queryParam("size", "-1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.sort").isArray());
    }

    @Test
    void get_bookings_no_filters_throws_exception() throws Exception {

        // given
        var page = new PageCriteria();
        page.setSize(10);
        page.setPage(0);
        page.setSort(Collections.emptySet());

        // when
        when(listBookingUseCase.listBookings(new BookingCriteria(), page))
                .thenThrow(RuntimeException.class);
        // then
        mockMvc.perform(
                        get("/api/bookings")
                                .queryParam("page", "0")
                                .queryParam("size", "10")
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    void get_bookings_no_filters_different_sort_goes_ok() throws Exception {

        // given
        var page = new PageCriteria();
        page.setSize(10);
        page.setPage(0);
        page.setSort(Collections.singleton(OrderCriteria.builder().field("name").direction(Direction.DESC).build()));

        // when
        when(listBookingUseCase.listBookings(new BookingCriteria(), page))
                .thenReturn(BookingList.builder()
                        .page(0)
                        .totalPages(1)
                        .bookings(Collections.singleton(Booking.builder().build()))
                        .size(10)
                        .sort(page.getSort())
                        .build());
        // then
        mockMvc.perform(
                        get("/api/bookings")
                                .queryParam("page", "0")
                                .queryParam("size", "10")
                                .queryParam("sort", "name,desc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sort").isArray())
                .andExpect(jsonPath("$.sort").isNotEmpty())
                .andExpect(jsonPath("$.sort[0].field").value("name"))
                .andExpect(jsonPath("$.sort[0].direction").value("DESC"));
    }

}
