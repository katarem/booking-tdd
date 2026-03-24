package io.github.katarem.domain.exception.impl.room;

import io.github.katarem.domain.exception.base.DomainNotFoundException;

public class RoomNotFoundException extends DomainNotFoundException {
    public RoomNotFoundException(Integer roomId) {
        super("Room", roomId.toString());
    }
}
