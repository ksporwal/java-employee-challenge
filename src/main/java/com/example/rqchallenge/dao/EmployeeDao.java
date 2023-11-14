package com.example.rqchallenge.dao;

import com.example.rqchallenge.exception.EmployeeNoDataFoundException;
import com.example.rqchallenge.model.Employee;
import com.example.rqchallenge.model.EmployeeResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class EmployeeDao implements IEmployeeDao{
    @Autowired
    RestTemplate restTemplate;
    @Value("${base.api.url}")
    String dummyUrl;
    @Autowired
    JSONParser jsonParser;
    @Override
    public EmployeeResponse getAllEmployees() throws HttpClientErrorException.TooManyRequests, EmployeeNoDataFoundException, JsonProcessingException {
        final String uri = dummyUrl + "/employees";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri,String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseEntity.getBody(),EmployeeResponse.class);
    }

    @Override
    public Employee getEmployeeById(String id) throws ParseException {
        final String uri = dummyUrl + "/employee/"+id;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri,String.class);
        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonResponse = (JSONObject) jsonParser.parse(responseEntity.getBody());
        JSONObject data = (JSONObject) Objects.requireNonNull(jsonResponse).get("data");
        return mapper.convertValue(data, Employee.class);
    }

    @Override
    public Employee createEmployee(Map<String, Object> employeeInput) throws ParseException {
        final String uri = dummyUrl + "/create";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(uri,employeeInput,String.class);
        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonResponse = (JSONObject) jsonParser.parse(responseEntity.getBody());
        JSONObject data = (JSONObject) Objects.requireNonNull(jsonResponse).get("data");
        return mapper.convertValue(data, Employee.class);
    }

    @Override
    public String deleteEmployeeById(String id) {
        final String uri = dummyUrl + "/delete/"+id;
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);
        Map<String, String> params = new HashMap<>();
        params.put("id", id);
        ResponseEntity<String> response = restTemplate.exchange(
                uri, HttpMethod.DELETE, entity, String.class, params);
        return response.getBody();
    }
}
