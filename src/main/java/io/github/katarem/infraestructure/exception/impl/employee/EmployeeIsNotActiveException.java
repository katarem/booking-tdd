package io.github.katarem.infraestructure.exception.impl.employee;

import io.github.katarem.infraestructure.exception.base.DomainBusinessException;

public class EmployeeIsNotActiveException extends DomainBusinessException {
    public EmployeeIsNotActiveException(Integer id) {
        super("Employee", "Employee " + id + " is not active.");
    }
}
