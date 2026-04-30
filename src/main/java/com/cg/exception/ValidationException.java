package com.cg.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.validation.FieldError;

public class ValidationException extends RuntimeException {

    private final List<FieldError> errors;

    // Constructor for list of field errors
    public ValidationException(List<FieldError> errors) {
        super("Validation failed");
        this.errors = (errors != null) ? new ArrayList<>(errors) : Collections.emptyList();
    }

    // Constructor for single field error
    public ValidationException(String field, String message) {
        super(message);
        List<FieldError> temp = new ArrayList<>();
        temp.add(new FieldError("object", field, message));
        this.errors = temp;
    }

    // Constructor for general/global error
    public ValidationException(String message) {
        super(message);
        List<FieldError> temp = new ArrayList<>();
        temp.add(new FieldError("object", "general", message));
        this.errors = temp;
    }

    public List<FieldError> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}