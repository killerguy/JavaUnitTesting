package com.mukul.dynamo.service;

import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.mukul.dynamo.domain.Employee;
import com.mukul.dynamo.domain.EmployeeStatus;
import com.mukul.dynamo.exception.BadRequestException;
import com.mukul.dynamo.exception.DuplicateRequestException;
import com.mukul.dynamo.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    public static final int ANY_DEPT_ID = 2;
    public static final String ANY_ID = "2";

    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void shouldReturnTrueIfEmployeeStatusIsActiveAndHaveAssociatedDepartment() {
        Optional<Employee> activeEmployee = ofNullable(buildEmployee(EmployeeStatus.ACTIVE.name(), ANY_DEPT_ID));
        when(employeeRepository.getEmployeeDetails(ANY_ID)).thenReturn(activeEmployee);

        boolean enabled = employeeService.isActiveEmployee(ANY_ID);

        assertThat(enabled, is(true));
    }

    @Test
    public void shouldReturnFalseIfEmployeeStatusIsActiveAndHaveNoAssociatedDepartment() {
        Optional<Employee> activeEmployee = ofNullable(buildEmployee(EmployeeStatus.ACTIVE.name(), 5));
        when(employeeRepository.getEmployeeDetails(ANY_ID)).thenReturn(activeEmployee);

        boolean enabled = employeeService.isActiveEmployee(ANY_ID);

        assertThat(enabled, is(false));
    }

    @Test
    public void shouldReturnFalseIfEmployeeStatusIsInactive() {
        Optional<Employee> inactiveEmployee = ofNullable(buildEmployee(EmployeeStatus.INACTIVE.name(), ANY_DEPT_ID));
        when(employeeRepository.getEmployeeDetails(ANY_ID)).thenReturn(inactiveEmployee);

        boolean enabled = employeeService.isActiveEmployee(ANY_ID);

        assertThat(enabled, is(false));
    }

    @Test
    public void shouldReturnFalseIfEmployeeStatusIsTerminated() {
        Optional<Employee> inactiveEmployee = ofNullable(buildEmployee(EmployeeStatus.TERMINATED.name(), ANY_DEPT_ID));
        when(employeeRepository.getEmployeeDetails(ANY_ID)).thenReturn(inactiveEmployee);

        boolean enabled = employeeService.isActiveEmployee(ANY_ID);

        assertThat(enabled, is(false));
    }

    @Test
    public void shouldReturnFalseIfEmployeeIsNotPresentInDB() {
        when(employeeRepository.getEmployeeDetails(ANY_ID)).thenReturn(ofNullable(null));

        boolean enabled = employeeService.isActiveEmployee(ANY_ID);

        assertThat(enabled, is(false));
    }

    @Test(expected = DuplicateRequestException.class)
    public void shouldThrowDuplicateRequestExceptionIfEmployeeWithNameAlreadyExists() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE.name(), 5);
        doThrow(ConditionalCheckFailedException.class).when(employeeRepository).createIfNotExists(employee);

        employeeService.createEmployee(employee);

        verify(employeeRepository).createIfNotExists(employee);
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowBadRequestExceptionIfEmployeeWithInvalidData() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE.name(), 5);
        doThrow(BadRequestException.class).when(employeeRepository).createIfNotExists(employee);

        employeeService.createEmployee(employee);

        verify(employeeRepository).createIfNotExists(employee);
    }

    @Test
    public void shouldCreateNewEmployeeDetails() {
        Employee employee = buildEmployee(EmployeeStatus.ACTIVE.name(), 5);
        doNothing().when(employeeRepository).createIfNotExists(employee);

        employeeService.createEmployee(employee);

        verify(employeeRepository).createIfNotExists(employee);
    }

    @Test
    public void shouldReturnListOfAllEmployeeDetails() {
        List<Employee> expectedEmployeeList = Arrays.asList(buildEmployee(EmployeeStatus.ACTIVE.name(), 2), buildEmployee(EmployeeStatus.ACTIVE.name(), 1));
        when(employeeRepository.getAllEmployees()).thenReturn(expectedEmployeeList);

        List<Employee> actualEmployeeList = employeeService.getAllEmployees();

        verify(employeeRepository).getAllEmployees();
        assertThat(expectedEmployeeList, is(actualEmployeeList));
    }

    private Employee buildEmployee(String status, int deptId) {
        Employee employee = new Employee();
        employee.setId(ANY_ID);
        employee.setStatus(status);
        employee.setDeptId(deptId);
        return employee;
    }

}