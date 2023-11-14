package com.example.rqchallenge.service;
import com.example.rqchallenge.exception.EmployeeNoDataFoundException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;

import java.util.List;
import java.util.Map;

public interface IEmployeeService {
    public EmployeeResponse getAllEmployees() throws EmployeeNoDataFoundException, JsonProcessingException;

    public List<Employee> getEmployeesByNameSearch(String searchString) throws EmployeeNoDataFoundException;

    public Employee getEmployeeById(String id) throws EmployeeNoDataFoundException;

    public Integer getHighestSalaryOfEmployees() throws EmployeeNoDataFoundException, JsonProcessingException;

    public List<String> getTopTenHighestEarningEmployeeNames() throws EmployeeNoDataFoundException, JsonProcessingException;

    public Employee createEmployee(Map<String, Object> employeeInput) throws ParseException;

    public String deleteEmployee(String id);
}
