package io.github.katarem.unit.infraestructure.adapter.output;

import io.github.katarem.application.port.output.BookingOutputPort;
import io.github.katarem.domain.model.Booking;
import io.github.katarem.domain.model.BookingStatus;
import io.github.katarem.infraestructure.adapter.output.persistence.BookingEntity;
import io.github.katarem.infraestructure.adapter.output.persistence.BookingJpaOutput;
import io.github.katarem.infraestructure.adapter.output.persistence.BookingPersistenceAdapter;
import io.github.katarem.infraestructure.mapper.BookingPersistenceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookingOutputAdapterTest {

    BookingOutputPort outputPort;

    @Mock
    BookingJpaOutput jpaOutput;

    BookingPersistenceMapper mapper = Mappers.getMapper(BookingPersistenceMapper.class);

    @BeforeEach
    void setUp() {
        outputPort = new BookingPersistenceAdapter(jpaOutput, mapper);
    }

    @Test
    void get_book_should_return_book() {

        // given
        UUID id = UUID.randomUUID();
        BookingEntity entity = BookingEntity.builder()
                .id(id)
                .build();

        // when
        when(jpaOutput.findById(id))
                .thenReturn(Optional.of(entity));

        // then
        Booking obtained = outputPort.getBooking(id);
        assertThat(obtained)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", id);
    }

    @Test
    void get_book_should_throw() {

        // given
        UUID id = UUID.randomUUID();

        // when
        when(jpaOutput.findById(id))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> outputPort.getBooking(id))
                .isInstanceOf(RuntimeException.class);
    }
    @Test
    void create_book_should_return() {

        // given
        Booking booking = Booking.builder()
                .build();

        // when
        when(jpaOutput.save(any()))
                .thenReturn(BookingEntity.builder().id(UUID.randomUUID()).build());

        // then
        Booking obtained = outputPort.upsertBooking(booking);
        assertThat(obtained)
                .isNotNull()
                .hasAllNullFieldsOrPropertiesExcept("id");
    }

    @Test
    void create_book_should_throw() {

        // given
        Booking booking = Booking.builder().build();

        // when
        when(jpaOutput.save(any()))
                .thenThrow(RuntimeException.class);

        // then
        assertThatThrownBy(() -> outputPort.upsertBooking(booking))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Get Bookings by Room Id should return bookings")
    void get_bookings_by_room_id_should_return() {

        // given
        Integer roomId = 1;
        Set<BookingEntity> bookings = Collections.singleton(BookingEntity.builder().roomId(roomId).build());

        // when
        when(jpaOutput.findAllByRoomIdAndStatusNot(roomId, BookingStatus.CANCELLED))
                .thenReturn(bookings);

        // then
        Set<Booking> result = outputPort.getAllBookingsByRoomId(roomId);
        assertThat(result)
                .isNotNull()
                .isNotEmpty();

    }

    @Test
    @DisplayName("Get Bookings by Room Id should return empty set")
    void get_bookings_by_room_id_should_return_empty() {

        // given
        Integer roomId = 1;
        Set<BookingEntity> bookings = Collections.singleton(BookingEntity.builder().roomId(roomId).build());

        // when
        when(jpaOutput.findAllByRoomIdAndStatusNot(roomId, BookingStatus.CANCELLED))
                .thenReturn(bookings);

        // then
        Set<Booking> result = outputPort.getAllBookingsByRoomId(roomId);
        assertThat(result)
                .isNotNull()
                .isNotEmpty();

    }

    @Test
    @DisplayName("Get Bookings by Room Id should throw exception")
    void get_bookings_by_room_id_should_throw() {

        // given
        Integer roomId = 1;

        // when
        when(jpaOutput.findAllByRoomIdAndStatusNot(roomId, BookingStatus.CANCELLED))
                .thenThrow(RuntimeException.class);

        // then
        assertThatThrownBy(() -> outputPort.getAllBookingsByRoomId(roomId))
                .isInstanceOf(RuntimeException.class);

    }

}
