package com.example.rqchallenge.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;
@Data
public class EmployeeResponse {
    @JsonProperty("status")
    public String status;
    @JsonProperty("data")
    public List<Employee> data;
    @JsonProperty("message")
    public String message;
    public List<Employee> getData() {
        return data;
    }
}
