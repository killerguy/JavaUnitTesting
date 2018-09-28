package com.mukul.dynamo.controller;

import com.mukul.dynamo.domain.Employee;
import com.mukul.dynamo.domain.EmployeeStatus;
import com.mukul.dynamo.exception.BadRequestException;
import com.mukul.dynamo.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
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
    public void shouldCreateNewEmployee() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE.name(), 5);
        doNothing().when(employeeService).createEmployee(employee);

        employeeController.createEmployee(employee);

        verify(employeeService).createEmployee(employee);
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionForDuplicateEmployee() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE.name(), 1);

        doThrow(BadRequestException.class).when(employeeService).createEmployee(employee);

        employeeController.createEmployee(employee);
    }

    @Test
    public void shouldReturnAllEmployeeDetails() {
        List<Employee> expectedEmployeeList = Arrays.asList(buildEmployee(EmployeeStatus.ACTIVE.name(), 1), buildEmployee(EmployeeStatus.ACTIVE.name(), 2));
        when(employeeService.getAllEmployees()).thenReturn(expectedEmployeeList);

        List<Employee> actualEmployeeList = employeeController.getAllEmployees();

        verify(employeeService).getAllEmployees();
        assertThat(expectedEmployeeList, is(actualEmployeeList));
    }

    private Employee buildEmployee(String status, int deptId) {
        Employee employee = new Employee();
        employee.setDeptId(deptId);
        employee.setId(ANY_ID);
        employee.setStatus(status);
        return employee;
    }

}