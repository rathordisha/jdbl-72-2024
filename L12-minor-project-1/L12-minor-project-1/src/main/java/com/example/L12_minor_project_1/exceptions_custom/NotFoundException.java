package com.example.L12_minor_project_1.exceptions_custom;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
