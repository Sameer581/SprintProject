package com.cg.exception;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Validation Exception (merged logic)
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidation(ValidationException ex) {

        Map<String, List<String>> errMap = new HashMap<>();

        for (FieldError fr : ex.getErrors()) {
            errMap
                .computeIfAbsent(fr.getField(), key -> new ArrayList<>())
                .add(fr.getDefaultMessage());
        }

        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }

    // Invalid JSON / Date format (his feature)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidJson(HttpMessageNotReadableException ex) {

        String msg = "Invalid request format";

        if (ex.getMessage() != null && ex.getMessage().contains("LocalDate")) {
            msg = "Enter the date in yyyy-MM-dd format";
        }

        return build(HttpStatus.BAD_REQUEST, msg);
    }

    // Resource Not Found (your feature)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Not Available (merged support)
    @ExceptionHandler(NotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleNotAvailable(NotAvailableException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Duplicate Resource (your feature)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(DuplicateResourceException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    // Unauthorized (your feature)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        return build(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    // Generic Exception (merged)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage());
    }

    // Common builder (your clean design)
    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        return new ResponseEntity<>(
                new ErrorResponse(status.value(), message, LocalDateTime.now()),
                status
        );
    }
}