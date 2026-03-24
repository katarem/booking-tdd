package io.github.katarem.domain.exception.impl.room;

import io.github.katarem.domain.exception.base.DomainBusinessException;

public class RoomIsInactiveException extends DomainBusinessException {

    public RoomIsInactiveException(Integer id) {
        super("Room", "Room with id " + id + " is inactive");
    }
}
