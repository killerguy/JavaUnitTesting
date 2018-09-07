package com.mukul.dynamo.controller;

import com.mukul.dynamo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = "/employee/{key}", produces = APPLICATION_JSON_VALUE)
    public boolean isActiveEmployee(@PathVariable int employeeId) {
        return employeeService.isActiveEmployee(employeeId);
    }

}
