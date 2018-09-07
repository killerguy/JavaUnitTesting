package com.mukul.dynamo.service;

import com.mukul.dynamo.domain.Department;
import com.mukul.dynamo.domain.DepartmentEnum;
import com.mukul.dynamo.domain.Employee;
import com.mukul.dynamo.domain.EmployeeStatus;
import com.mukul.dynamo.repository.EmployeeRepository;
import javafx.scene.control.Toggle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

import static com.mukul.dynamo.domain.EmployeeStatus.*;

public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public boolean isActiveEmployee(int employeeKey) {
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
}
