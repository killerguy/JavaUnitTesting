package com.mukul.dynamo.controller;

import com.mukul.dynamo.domain.Employee;
import com.mukul.dynamo.domain.EmployeeStatus;
import com.mukul.dynamo.exception.BadRequestException;
import com.mukul.dynamo.service.EmployeeService;
import javafx.scene.control.Toggle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    public static final String ANY_ID = "5";
    @InjectMocks
    EmployeeController employeeController;
    @Mock
    EmployeeService employeeService;

    @Test
    public void shouldReturnActiveEmployeeStatus() throws Exception {
        when(employeeService.isActiveEmployee(ANY_ID)).thenReturn(true);

        boolean isActive = employeeController.isActiveEmployee(ANY_ID);

        assertThat(isActive, is(true));
    }

    @Test
    public void shouldReturnInactiveEmployeeStatus() throws Exception {
        when(employeeService.isActiveEmployee(ANY_ID)).thenReturn(false);

        boolean isActive = employeeController.isActiveEmployee(ANY_ID);

        assertThat(isActive, is(false));
    }

    @Test
    public void shouldCreateTheToggle() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE.name(), 5);
        doNothing().when(employeeService).createEmployee(employee);

        employeeController.createEmployee(employee);

        verify(employeeService).createEmployee(employee);
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionForDuplicateToggle() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE.name(), 1);

        doThrow(BadRequestException.class).when(employeeService).createEmployee(employee);

        employeeController.createEmployee(employee);
    }

    private Employee buildEmployee(String status, int deptId) {
        Employee employee = new Employee();
        employee.setDeptId(deptId);
        employee.setId(ANY_ID);
        employee.setStatus(status);
        return employee;
    }

}