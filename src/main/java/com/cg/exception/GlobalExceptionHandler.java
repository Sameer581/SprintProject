package com.cg.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.dto.ErrorMessageDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleInvalidJson(HttpMessageNotReadableException ex) {

        String msg = "Invalid request format";

        if (ex.getMessage() != null && ex.getMessage().contains("LocalDate")) {
            msg = "Enter the date in yyyy-MM-dd format";
        }

        ErrorMessageDto dto = new ErrorMessageDto();
        dto.setErrorMsg(msg);
        dto.setTimeStamp(LocalDateTime.now());
        dto.setStatus(HttpStatus.BAD_REQUEST.toString());

        return dto;
    }
    
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleValidation(ValidationException ex) {

        Map<String, List<String>> errMap = new HashMap<>();

        List<FieldError> errors = ex.getErrors();

        for (FieldError fr : errors) {
            errMap
                .computeIfAbsent(fr.getField(), key -> new ArrayList<>())
                .add(fr.getDefaultMessage());
        }

        ErrorMessageDto dto = new ErrorMessageDto();
        dto.setErrorMsg("Validation failed");
        dto.setErrMap(errMap);
        dto.setTimeStamp(LocalDateTime.now());
        dto.setStatus(HttpStatus.BAD_REQUEST.toString());

        return dto;
    }

    @ExceptionHandler(NotAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDto handleNotFound(NotAvailableException ex) {

        ErrorMessageDto dto = new ErrorMessageDto();
        dto.setErrorMsg(ex.getMessage());
        dto.setTimeStamp(LocalDateTime.now());
        dto.setStatus(HttpStatus.NOT_FOUND.toString());

        return dto;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDto handleGeneric(Exception ex) {

        ErrorMessageDto dto = new ErrorMessageDto();
        dto.setErrorMsg("Unexpected error: " + ex.getMessage());
        dto.setTimeStamp(LocalDateTime.now());
        dto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return dto;
    }
}