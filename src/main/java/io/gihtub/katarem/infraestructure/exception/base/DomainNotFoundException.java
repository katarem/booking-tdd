package io.gihtub.katarem.infraestructure.exception.base;

public abstract class DomainNotFoundException extends DomainBaseException {
    public DomainNotFoundException(String modelName, String id) {
        super(modelName.toUpperCase() + "_NOT_FOUND", modelName + " with id " + id + " was not found");
    }
}
