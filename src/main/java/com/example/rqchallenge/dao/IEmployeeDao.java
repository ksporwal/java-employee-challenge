package com.example.rqchallenge.dao;

import com.example.rqchallenge.exception.EmployeeNoDataFoundException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public interface IEmployeeDao {
    EmployeeResponse getAllEmployees() throws EmployeeNoDataFoundException, JsonProcessingException;
    Employee getEmployeeById(String id) throws IOException, ParseException;

    Employee createEmployee(Map<String, Object> employeeInput) throws ParseException;

    String deleteEmployeeById(String id);
}
