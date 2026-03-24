package io.github.katarem.application.port.output;

public interface EmployeeQueryPort {
    boolean existsById(Integer employeeId);
}
