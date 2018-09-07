package com.mukul.dynamo.repository;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.mukul.dynamo.domain.Employee;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Component
public class EmployeeRepository {

    private DynamoDBMapper mapper = null;

    public EmployeeRepository() {
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(new DefaultAWSCredentialsProviderChain());
        dynamoDBClient.withRegion(Regions.DEFAULT_REGION);
        mapper = new DynamoDBMapper(dynamoDBClient);
    }


    public Optional getEmployeeDetails(int employeeKey) {
        Employee employee = null;
        try {
            employee = mapper.load(Employee.class, employeeKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ofNullable(employee);
    }
}
