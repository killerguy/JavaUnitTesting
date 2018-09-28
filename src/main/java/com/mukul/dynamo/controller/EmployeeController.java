package com.mukul.dynamo.controller;

import com.mukul.dynamo.domain.Employee;
import com.mukul.dynamo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/employee/{key}", produces = APPLICATION_JSON_VALUE)
    public boolean isActiveEmployee(@PathVariable String employeeId) {
        return employeeService.isActiveEmployee(employeeId);
    }

    @PostMapping(value = "/employees", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public void createEmployee(@RequestBody Employee employee) {
        employeeService.createEmployee(employee);
    }

    @GetMapping(value = "/employees", produces = APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

}
