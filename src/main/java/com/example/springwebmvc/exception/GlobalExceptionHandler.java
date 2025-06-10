package com.example.springwebmvc.exception;

import com.example.springwebmvc.base.BaseError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Business Exception Handler
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<BaseError> handleResponseStatus(ResponseStatusException ex) {
        BaseError baseError = BaseError.builder()
                .status(false)
                .code(ex.getStatusCode().value())
                .message(ex.getReason())
                .errors(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(ex.getStatusCode()).body(baseError);
    }

    // Validation Exception Handler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseError> handleValidationEx(MethodArgumentNotValidException ex) {

        List<Map<String, String>> errors = new ArrayList<>();
        for (FieldError fieldError : ex.getFieldErrors()) {
            Map<String, String> error = new LinkedHashMap<>();
            error.put("field", fieldError.getField());
            error.put("detail", fieldError.getDefaultMessage());
            errors.add(error);
        }

        int errorCount = errors.size();
        String message = errorCount == 0
                ? "Invalid request"
                : "Validation failed: " + errorCount + " field" + (errorCount > 1 ? "s" : "") + " have error(s)";

        BaseError baseError = BaseError.builder()
                .status(false)
                .code(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseError);
    }

    // Data Integrity Violation Handler
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseError> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        BaseError baseError = BaseError.builder()
                .status(false)
                .code(HttpStatus.CONFLICT.value())
                .message("Data integrity violation!")
                .errors(ex.getMostSpecificCause().getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(baseError);
    }

    // Global Exception Handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseError> handleMainException(Exception ex) {
        BaseError baseError = BaseError.builder()
                .status(false)
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Something went wrong!")
                .errors(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseError);
    }
}
