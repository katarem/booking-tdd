package io.github.katarem.infraestructure.adapter.output.client.dto;

public record RoomDto(
        Integer id,
        Integer capacity,
        boolean active
) {
}
