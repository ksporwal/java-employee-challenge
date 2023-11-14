package com.example.rqchallenge.service;

import com.example.rqchallenge.dao.IEmployeeDao;
import com.example.rqchallenge.exception.EmployeeNoDataFoundException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Service
public class EmployeeService implements IEmployeeService{
    @Autowired
    IEmployeeDao employeeDAO;
    public static Logger log = LogManager.getLogger(EmployeeService.class);
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 5000))
    @Override
    public EmployeeResponse getAllEmployees() throws HttpClientErrorException.TooManyRequests, EmployeeNoDataFoundException, JsonProcessingException {
        return employeeDAO.getAllEmployees();
    }
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 5000))
    @Override
    public List<Employee> getEmployeesByNameSearch(String empName) throws EmployeeNoDataFoundException {
        try {
            EmployeeResponse response = employeeDAO.getAllEmployees();
            List<Employee> employees = response.getData();
            List<Employee> employeeNames = employees.stream()
                    .filter(e -> e.getName().toLowerCase().matches("^.*" + empName.toLowerCase() + ".*$"))
                    .collect(Collectors.toList());
            if(employeeNames.isEmpty()) {
                log.error("Could not get employees data from API");
                throw new EmployeeNoDataFoundException("Could not get data for employee by name " + empName + " API ", HttpStatus.NOT_FOUND);
            }
            return employeeNames;
        } catch (HttpClientErrorException.TooManyRequests | EmployeeNoDataFoundException e) {
            log.error("Too many requests", e.getMessage(), e);
            throw new EmployeeNoDataFoundException("Too many requests: " + HttpStatus.TOO_MANY_REQUESTS,HttpStatus.NOT_FOUND);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee getEmployeeById(String id) throws EmployeeNoDataFoundException {
        try {
            Employee response = employeeDAO.getEmployeeById(id);
            if(response == null) {
                log.error("Could not get employees data from API");
                throw new EmployeeNoDataFoundException("Could not get employee with id: " + id + " from data source ", HttpStatus.NOT_FOUND);
            }
            return response;
        } catch (HttpClientErrorException.TooManyRequests e) {
            log.error("Too many requests", e.getMessage(), e);
            throw new EmployeeNoDataFoundException("Too many requests: " + HttpStatus.TOO_MANY_REQUESTS);
        }  catch (EmployeeNoDataFoundException | IOException | ParseException e) {
            log.error("Exception occured:",e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getHighestSalaryOfEmployees() throws EmployeeNoDataFoundException, JsonProcessingException {
        EmployeeResponse resp = employeeDAO.getAllEmployees();
        List<Employee> employees = resp.getData();
        return employees.stream().map(Employee::getSalary).max(Integer::compare).get();
    }

    @Override
    public List<String> getTopTenHighestEarningEmployeeNames() throws EmployeeNoDataFoundException, JsonProcessingException {
        try {
            EmployeeResponse response = employeeDAO.getAllEmployees();
            List<Employee> employees = response.getData();
            return employees.stream().sorted(Comparator.comparing(Employee::getSalary, Comparator.reverseOrder())).limit(10)
                    .map(Employee::getName).collect(Collectors.toList());
        } catch (HttpClientErrorException.TooManyRequests e) {
            log.error("Too many requests", e.getMessage(), e);
            throw new EmployeeNoDataFoundException("Too many requests: " + HttpStatus.TOO_MANY_REQUESTS);
        } catch (Exception e) {
            log.error("Could not get employees data from API", e.getMessage(), e);
            throw new EmployeeNoDataFoundException("Could not fetch top 10 highest earning employees from API with Reason " + HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Employee createEmployee(Map<String, Object> employeeInput) throws ParseException {
        return employeeDAO.createEmployee(employeeInput);
    }

    @Override
    public String deleteEmployee(String id) {
        return employeeDAO.deleteEmployeeById(id);
    }
}
