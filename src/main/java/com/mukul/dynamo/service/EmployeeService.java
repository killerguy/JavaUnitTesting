package com.mukul.dynamo.service;

import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.mukul.dynamo.domain.DepartmentEnum;
import com.mukul.dynamo.domain.Employee;
import com.mukul.dynamo.exception.BadRequestException;
import com.mukul.dynamo.exception.DuplicateRequestException;
import com.mukul.dynamo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mukul.dynamo.domain.EmployeeStatus.ACTIVE;
import static java.lang.String.format;

public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    public static final String BAD_REQUEST = "Bad Request";
    private final static String DUPLICATE_EMPLOYEE_ERROR_MESSAGE = "Employee with name %s already exists";

    public boolean isActiveEmployee(String employeeKey) {
        boolean isActive = false;
        Optional<Employee> employeeOptional = employeeRepository.getEmployeeDetails(employeeKey);

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();

            if (employee.getStatus().equalsIgnoreCase(ACTIVE.name())) {
                isActive = isAssociatedDeparment(employee.getDeptId());
            }
        }
        return isActive;
    }

    private boolean isAssociatedDeparment(int deptId) {
        return Arrays.stream(DepartmentEnum.values()).anyMatch(department -> department.getDepartmentId() == deptId);
    }

    public void createEmployee(Employee employee) {
        try {
            employeeRepository.createIfNotExists(employee);
        } catch (ConditionalCheckFailedException ex) {
            String errorMessage = format(DUPLICATE_EMPLOYEE_ERROR_MESSAGE, employee.getFirstName() + employee.getLastName());
            throw new DuplicateRequestException(errorMessage);
        } catch (Exception ex) {
            throw new BadRequestException(BAD_REQUEST);
        }
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

}
