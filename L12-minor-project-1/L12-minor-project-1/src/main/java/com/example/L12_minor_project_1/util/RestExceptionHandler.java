package com.example.L12_minor_project_1.util;

import com.example.L12_minor_project_1.exceptions_custom.BadRequestException;
import com.example.L12_minor_project_1.exceptions_custom.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(final BadRequestException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(final NotFoundException exception){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleNotFoundException(final RuntimeException exception){
        return ResponseEntity.internalServerError().body(exception.getMessage());
    }
}
