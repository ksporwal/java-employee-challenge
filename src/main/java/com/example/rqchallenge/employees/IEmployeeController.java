package com.example.rqchallenge.employees;

import com.example.rqchallenge.exception.EmployeeNoDataFoundException;
import com.example.rqchallenge.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public interface IEmployeeController {

    @GetMapping("/employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Data found"),
            @ApiResponse(responseCode = "404", description = "Data Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    }
    )
    ResponseEntity<List<Employee>> getAllEmployees() throws IOException, EmployeeNoDataFoundException;

    @GetMapping("/search/{searchString}")
    ResponseEntity<List<Employee>> getEmployeesByNameSearch(@PathVariable String searchString) throws EmployeeNoDataFoundException;

    @GetMapping("/{id}")
    ResponseEntity<Employee> getEmployeeById(@PathVariable String id) throws EmployeeNoDataFoundException;

    @GetMapping("/highestSalary")
    ResponseEntity<Integer> getHighestSalaryOfEmployees() throws EmployeeNoDataFoundException, JsonProcessingException;

    @GetMapping("/topTenHighestEarningEmployeeNames")
    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() throws EmployeeNoDataFoundException, JsonProcessingException;

    @PostMapping()
    ResponseEntity<Employee> createEmployee(@RequestBody Map<String, Object> employeeInput) throws ParseException;

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteEmployeeById(@PathVariable String id);

}
