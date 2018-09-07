package com.mukul.dynamo.controller;

import com.mukul.dynamo.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    public static final int ANY_ID = 5;
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
}