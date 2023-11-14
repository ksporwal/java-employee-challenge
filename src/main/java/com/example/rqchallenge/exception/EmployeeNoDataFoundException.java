package com.example.rqchallenge.exception;

import org.springframework.http.HttpStatus;

public class EmployeeNoDataFoundException extends Exception{
    public EmployeeNoDataFoundException(String message) {
        super(message);
    }

    public EmployeeNoDataFoundException(String message, HttpStatus notFound) {
        super(message);
    }
}
