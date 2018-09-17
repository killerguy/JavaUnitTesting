package com.mukul.dynamo.repository;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.mukul.dynamo.domain.Employee;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.amazonaws.util.ImmutableMapParameter.of;
import static java.util.Optional.ofNullable;

@Component
public class EmployeeRepository {

    private DynamoDBMapper mapper = null;
    private static final String EMPLOYEE_PRIMARY_KEY = "id";

    public EmployeeRepository() {
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(new DefaultAWSCredentialsProviderChain());
        dynamoDBClient.withRegion(Regions.DEFAULT_REGION);
        mapper = new DynamoDBMapper(dynamoDBClient);
    }


    public Optional getEmployeeDetails(String employeeKey) {
        Employee employee = null;
        try {
            employee = mapper.load(Employee.class, employeeKey);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ofNullable(employee);
    }

    public void createIfNotExists(Employee employee) {
        save(employee, ComparisonOperator.NE);
    }

    private void save(Employee employee , ComparisonOperator operator) {
        DynamoDBSaveExpression saveExpression = createExpression(EMPLOYEE_PRIMARY_KEY, employee.getId(),operator);
        employee.setUpdatedTime(new DateTime().toString());
        mapper.save(employee, saveExpression);
    }

    private DynamoDBSaveExpression createExpression(String attrName, String attrValue , ComparisonOperator operator) {
        DynamoDBSaveExpression saveExpression = new DynamoDBSaveExpression();
        saveExpression.setExpected(of(attrName,
                new ExpectedAttributeValue(new AttributeValue(attrValue)).withComparisonOperator(operator)));
        return saveExpression;
    }
}
