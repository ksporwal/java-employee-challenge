package com.example.rqchallenge.controller;

import com.example.rqchallenge.employees.IEmployeeController;
import com.example.rqchallenge.exception.EmployeeNoDataFoundException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import com.example.rqchallenge.service.IEmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class EmployeeController implements IEmployeeController {
    @Autowired
    IEmployeeService employeeService;
    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() throws HttpClientErrorException.TooManyRequests, EmployeeNoDataFoundException, JsonProcessingException {
        EmployeeResponse empResp = employeeService.getAllEmployees();
        return new ResponseEntity<>(empResp.getData(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) throws EmployeeNoDataFoundException {
        return new ResponseEntity<>(employeeService.getEmployeesByNameSearch(searchString), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) throws EmployeeNoDataFoundException {
        return new ResponseEntity<>(employeeService.getEmployeeById(id), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() throws EmployeeNoDataFoundException, JsonProcessingException {
        final Integer highestSalary = Math.toIntExact(employeeService.getHighestSalaryOfEmployees());
        return new ResponseEntity<>(highestSalary, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() throws EmployeeNoDataFoundException, JsonProcessingException {
        return new ResponseEntity<>(employeeService.getTopTenHighestEarningEmployeeNames(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) throws ParseException {
        return new ResponseEntity<>(employeeService.createEmployee(employeeInput), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        return new ResponseEntity<>(employeeService.deleteEmployee(id), HttpStatus.OK);
    }
}
