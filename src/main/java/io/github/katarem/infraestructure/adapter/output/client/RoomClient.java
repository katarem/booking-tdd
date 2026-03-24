package io.github.katarem.infraestructure.adapter.output.client;

import io.github.katarem.infraestructure.adapter.output.client.dto.RoomDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoomClient {

    private final List<RoomDto> rooms = List.of(
            new RoomDto(1, 10, true),
            new RoomDto(2, 5, true),
            new RoomDto(3, 30, false),
            new RoomDto(4, 3, true),
            new RoomDto(5, 3, false),
            new RoomDto(6, 10, true),
            new RoomDto(7, 2, true),
            new RoomDto(8, 4, true),
            new RoomDto(9, 20, false),
            new RoomDto(10, 30, true)
    );

    public Optional<RoomDto> getRoomById(Integer id) {
        return rooms.stream()
                .filter(room -> room.id().equals(id))
                .findFirst();
    }

}
