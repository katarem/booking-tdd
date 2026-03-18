package io.gihtub.katarem.web;

import io.gihtub.katarem.application.port.input.GetBookingUseCase;
import io.gihtub.katarem.domain.model.Booking;
import io.gihtub.katarem.infraestructure.adapter.input.rest.BookingApi;
import io.gihtub.katarem.infraestructure.exception.BookingExceptionHandler;
import io.gihtub.katarem.infraestructure.exception.BookingNotFoundException;
import io.gihtub.katarem.infraestructure.mapper.BookingRestMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureWebMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingApi.class)
@Import({BookingRestMapperImpl.class, BookingExceptionHandler.class})
@AutoConfigureWebMvc
public class BookingApiTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    GetBookingUseCase getBookingUseCase;

    @Test
    @DisplayName("Get Booking returns OK")
    void test1() throws Exception {

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
        mockMvc.perform(get("/api/booking/" + bookingId))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    @DisplayName("Get Booking returns Not Found")
    void test2() throws Exception {

        // given
        UUID bookingId = UUID.randomUUID();

        String expected = String.format("Booking %s not found.", bookingId);

        // when
        when(getBookingUseCase.getBooking(bookingId))
                .thenThrow(new BookingNotFoundException(bookingId));

        // then
        mockMvc.perform(get("/api/booking/" + bookingId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(expected))
                .andExpect(jsonPath("$.code").value("BOOKING_NOT_FOUND"))
                .andExpect(jsonPath("$.timestamp").exists());

    }
}
