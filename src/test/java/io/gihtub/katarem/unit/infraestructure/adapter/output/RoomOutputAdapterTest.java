package io.gihtub.katarem.unit.infraestructure.adapter.output;

import io.gihtub.katarem.application.port.output.RoomQueryPort;
import io.gihtub.katarem.domain.model.Room;
import io.gihtub.katarem.infraestructure.adapter.output.client.RoomClient;
import io.gihtub.katarem.infraestructure.adapter.output.client.RoomOutputAdapter;
import io.gihtub.katarem.infraestructure.adapter.output.client.dto.RoomDto;
import io.gihtub.katarem.infraestructure.mapper.RoomMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoomOutputAdapterTest {

    RoomQueryPort roomQueryPort;

    @Mock
    RoomClient client;

    RoomMapper mapper = Mappers.getMapper(RoomMapper.class);

    @BeforeEach
    void setUp() {
        roomQueryPort = new RoomOutputAdapter(client, mapper);
    }

    @Test
    void get_room_room_exists() {
        // given
        Integer roomId = 1;

        // when
        when(client.getRoomById(1))
                .thenReturn(Optional.of(new RoomDto(1, 10, true)));

        // then
        Room obtained = roomQueryPort.getRoomById(roomId);
        assertThat(obtained)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", roomId);
    }

    @Test
    void get_room_room_does_not_exist() {
        // given
        Integer roomId = 1;
        // when
        when(client.getRoomById(1))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> roomQueryPort.getRoomById(roomId))
                .isInstanceOf(RuntimeException.class);
    }

}
