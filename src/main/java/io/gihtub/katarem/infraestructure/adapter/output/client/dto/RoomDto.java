package io.gihtub.katarem.infraestructure.adapter.output.client.dto;

public record RoomDto(
        Integer id,
        Integer capacity,
        boolean active
) {
}
