package io.gihtub.katarem.infraestructure.exception.impl.room;

import io.gihtub.katarem.infraestructure.exception.base.DomainBusinessException;

public class InvalidCapacityForRoomException extends DomainBusinessException {
    public InvalidCapacityForRoomException(Integer id) {
        super("Room", "Invalid attendee amount for room " + id);
    }
}
