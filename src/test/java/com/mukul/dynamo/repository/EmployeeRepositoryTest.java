package com.mukul.dynamo.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.mukul.dynamo.domain.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.core.Is.is;
import static org.joda.time.DateTimeUtils.setCurrentMillisFixed;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeRepositoryTest {

    public static final String ANY_ID = "05";
    @InjectMocks
    private EmployeeRepository employeeRepository;

    @Mock
    private DynamoDBMapper mapper;
    @Mock
    PaginatedScanList paginatedScanList;

    ArgumentCaptor<Employee> employeeCapture;
    ArgumentCaptor<DynamoDBSaveExpression> dDBExpressionCaptor;
    Employee employee;
    Date expectedDateTime;

    @Before
    public void setUp() throws Exception {
        employeeCapture = ArgumentCaptor.forClass(Employee.class);
        dDBExpressionCaptor = ArgumentCaptor.forClass(DynamoDBSaveExpression.class);
        employee = buildEmployee();
        expectedDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SSS").parse("01/07/2018 16:45:00:000");
        setCurrentMillisFixed(expectedDateTime.getTime());
    }


    @Test
    public void shouldReturnDataFromDynamo() throws Exception {
        Employee employee = new Employee();
        when(mapper.load(any(), anyInt())).thenReturn(employee);

        Optional actualDynamoData = employeeRepository.getEmployeeDetails(ANY_ID);

        assertEquals(employee, actualDynamoData.get());
    }

    @Test
    public void shouldReturnNullOptionalIfDynamoConnectThrowsError() throws Exception {
        when(mapper.load(any(), anyInt())).thenThrow(new IllegalArgumentException());

        Optional actualDynamoData = employeeRepository.getEmployeeDetails(ANY_ID);

        assertThat(actualDynamoData.isPresent(), is(false));
    }

    @Test
    public void shouldReturnAlEmployeeDetailsFromDynamo() throws Exception {
        when(mapper.scan(any(), any())).thenReturn(paginatedScanList);

        List<Employee> actualEmployees = employeeRepository.getAllEmployees();

        verify(paginatedScanList).loadAllResults();
        assertThat(actualEmployees, is(paginatedScanList));
    }

    private Employee buildEmployee() {
        Employee employee = new Employee();
        employee.setId(ANY_ID);
        return employee;
    }

}