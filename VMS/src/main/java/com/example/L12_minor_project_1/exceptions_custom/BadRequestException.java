package com.example.L12_minor_project_1.exceptions_custom;

public class BadRequestException extends RuntimeException{

    public BadRequestException(String message){
        super(message);
    }
}
