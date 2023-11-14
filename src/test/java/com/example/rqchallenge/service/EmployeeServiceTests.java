package com.example.rqchallenge.service;

import com.example.rqchallenge.exception.EmployeeNoDataFoundException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootTest
public class EmployeeServiceTests {
    @Mock
    RestTemplate restTemplate;
    private EmployeeService empService;
    //This gets executed before all test cases get executed
    @BeforeEach
    void setUp() {
        ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();

        empService = new EmployeeService();

        ReflectionTestUtils.setField(empService, "restTemplate", restTemplate);
        ReflectionTestUtils.setField(empService, "objectMapper", mapper);
        ReflectionTestUtils.setField(empService, "dummyRestApi", "thisIsDumyAPI");
        ReflectionTestUtils.setField(empService, "jsonParser", parser);
    }

    @Test
    void getEmployyessTests() throws EmployeeNoDataFoundException, JsonProcessingException {
        final String dummyResp = "{\"id\":7,\"employee_name\":\"Herrod Chandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"}";
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(dummyResp);
        final EmployeeResponse employeeDetails = empService.getAllEmployees();
        Assertions.assertFalse(employeeDetails.getData().isEmpty());
    }
    @Test
    void getEmployeeByEmployeeIdTests() throws EmployeeNoDataFoundException {
        final String dummyResp = "{\"id\":7,\"employee_name\":\"Herrod Chandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"}";
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(dummyResp);
        Assertions.assertNotNull(empService.getEmployeeById("1"));
    }
    @Test
    void getEmployeeByEmployeeIdWithStringTypeTests() throws EmployeeNoDataFoundException {
        try {
            empService.getEmployeeById("dummyid");
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof Exception);
        }
    }
    @Test
    void getEmployeeByNameTests() {
        try {
            empService.getEmployeesByNameSearch("dummyname");
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof EmployeeNoDataFoundException);
        }
    }
    @Test
    void getTopTenHighestEarningEmployeeNamesTests() throws EmployeeNoDataFoundException, JsonProcessingException {
        final String dummyResp = "{\"id\":7,\"employee_name\":\"Herrod Chandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"}";
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(dummyResp);
        final List<String> topTenEmpList = empService.getTopTenHighestEarningEmployeeNames();
        Assertions.assertEquals("Paul Byrd", topTenEmpList.get(0));
    }
    @Test
    void getEmployeesHighestSalaryTests() throws EmployeeNoDataFoundException, JsonProcessingException {
        final String dummyResp = "{\"id\":7,\"employee_name\":\"Herrod Chandler\",\"employee_salary\":137500,\"employee_age\":59,\"profile_image\":\"\"}";
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenReturn(dummyResp);
        final Long highestSalaryofEmployees = Long.valueOf(empService.getHighestSalaryOfEmployees());
        Assertions.assertEquals(725000, highestSalaryofEmployees);
    }
    @Test()
    void getEmployeeDetailsInternalServerError() {
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any())).thenThrow(HttpServerErrorException.InternalServerError.class);
        try {
            empService.getAllEmployees();
        } catch (Exception e) {
            Assertions.assertTrue(e instanceof HttpServerErrorException.InternalServerError);
        }
    }
}
