package com.example.rqchallenge.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    //@Schema(name = "Employee Id", example = "1")
    @JsonProperty("id")
    String id;

    //@Schema(name = "Employee Name", example = "sample")
    @JsonProperty("employee_name")
    String name;

    //@Schema(name = "Employee Salary", example = "100")
    @JsonProperty("employee_salary")
    int salary;

    //@Schema(name = "Profile Image", example = "sample_image_link")
    @JsonProperty("profile_image")
    //@JsonIgnore
    String profile_image;

    //@Schema(name = "Employee Age", example = "30")
    @JsonProperty("employee_age")
    int age;

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }
}