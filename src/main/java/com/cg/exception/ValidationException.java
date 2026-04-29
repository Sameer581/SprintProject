package com.cg.exception;

import java.util.Collections;
import java.util.List;

import org.springframework.validation.FieldError;

public class ValidationException extends RuntimeException {

    private final List<FieldError> errors;

    public ValidationException(String message) {
        super(message);
        this.errors = Collections.emptyList();
    }

    public ValidationException(List<FieldError> errors) {
        super("Validation failed");
        this.errors = (errors != null) ? errors : Collections.emptyList();
    }

    public List<FieldError> getErrors() {
        return errors;
    }
}