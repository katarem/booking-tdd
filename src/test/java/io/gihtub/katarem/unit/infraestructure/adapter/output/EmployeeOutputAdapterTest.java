package io.gihtub.katarem.unit.infraestructure.adapter.output;

import io.gihtub.katarem.application.port.output.EmployeeQueryPort;
import io.gihtub.katarem.infraestructure.adapter.output.client.EmployeeClient;
import io.gihtub.katarem.infraestructure.adapter.output.client.EmployeeOutputAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeOutputAdapterTest {

    EmployeeQueryPort employeeQueryPort;

    @Mock
    EmployeeClient client;

    @BeforeEach
    void setUp() {
        employeeQueryPort = new EmployeeOutputAdapter(client);
    }

    @Test
    @DisplayName("Employee returns OK when it exists")
    void employee_returns_ok_when_exists(){
        //given
        Integer employeeId = 1;
        //when
        when(client.existsById(1))
                .thenReturn(true);
        //then
        assertThat(employeeQueryPort.existsById(employeeId))
                .isEqualTo(true);
    }

    @Test
    @DisplayName("Employee returns false when it does not exist")
    void employee_returns_false_when_does_not_exist(){
        //given
        Integer employeeId = 1;
        //when
        when(client.existsById(1))
                .thenReturn(false);
        //then
        assertThat(employeeQueryPort.existsById(employeeId))
                .isEqualTo(false);

    }


}
