package io.github.katarem.infraestructure.exception.impl.room;

import io.github.katarem.infraestructure.exception.base.DomainBusinessException;

public class InvalidCapacityForRoomException extends DomainBusinessException {
    public InvalidCapacityForRoomException(Integer id) {
        super("Room", "Invalid attendee amount for room " + id);
    }
}
