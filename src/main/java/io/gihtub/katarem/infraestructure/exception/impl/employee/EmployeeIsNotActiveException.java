package io.gihtub.katarem.infraestructure.exception.impl.employee;

import io.gihtub.katarem.infraestructure.exception.base.DomainBusinessException;

public class EmployeeIsNotActiveException extends DomainBusinessException {
    public EmployeeIsNotActiveException(Integer id) {
        super("Employee", "Employee " + id + " is not active.");
    }
}
