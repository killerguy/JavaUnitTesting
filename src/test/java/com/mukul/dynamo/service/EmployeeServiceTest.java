package com.mukul.dynamo.service;

import com.mukul.dynamo.domain.Employee;
import com.mukul.dynamo.domain.EmployeeStatus;
import com.mukul.dynamo.repository.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    public static final int ANY_DEPT_ID = 2;
    public static final int ANY_ID = 2;

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
    public void shouldReturnFalseIfToggleIsNotPresentInDB() {
        when(employeeRepository.getEmployeeDetails(ANY_ID)).thenReturn(ofNullable(null));

        boolean enabled = employeeService.isActiveEmployee(ANY_ID);

        assertThat(enabled, is(false));
    }

    private Employee buildEmployee(String status, int deptId) {
        Employee employee = new Employee();
        employee.setId(ANY_ID);
        employee.setStatus(status);
        employee.setDeptId(deptId);
        return employee;
    }

}