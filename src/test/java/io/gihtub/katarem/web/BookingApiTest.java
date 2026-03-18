package io.gihtub.katarem.web;

import io.gihtub.katarem.application.port.input.CreateBookingUseCase;
import io.gihtub.katarem.application.port.input.GetBookingUseCase;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.domain.model.BookingStatus;
import io.gihtub.katarem.infraestructure.adapter.input.rest.BookingApi;
import io.gihtub.katarem.infraestructure.adapter.input.rest.BookingRequest;
import io.gihtub.katarem.infraestructure.exception.BookingExceptionHandler;
import io.gihtub.katarem.infraestructure.exception.BookingNotFoundException;
import io.gihtub.katarem.infraestructure.mapper.BookingRestMapperImpl;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingApi.class)
@Import({BookingRestMapperImpl.class, BookingExceptionHandler.class})
@AutoConfigureWebMvc
public class BookingApiTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    GetBookingUseCase getBookingUseCase;

    @MockitoBean
    CreateBookingUseCase createBookingUseCase;

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

        String expected = String.format("Booking %s not found.", bookingId);

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
        BookingRequest request = BookingRequest.builder()
                .title("")
                .description("Epic description")
                .startDateTime(ZonedDateTime.now())
                .endDateTime(ZonedDateTime.now())
                .roomId(1)
                .employeeId(1)
                .status(BookingStatus.PENDING)
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
}
