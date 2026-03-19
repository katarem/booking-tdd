package io.gihtub.katarem.infraestructure.exception.base;

public class DomainConflictException extends DomainBaseException {
    public DomainConflictException(String modelName, String message) {
        super(modelName, message);
    }
}
