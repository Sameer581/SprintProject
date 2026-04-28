package com.cg.web;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cg.dto.ErrorMessageDto;
import com.cg.exception.FriendshipAlreadyExistsException;
import com.cg.exception.InvalidFriendRequestException;
import com.cg.exception.NotAvailableException;
import com.cg.exception.UserNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDto handleUserNotFound(UserNotFoundException ex) {
        ErrorMessageDto dto = new ErrorMessageDto();
        dto.setErrorMsg(ex.getMessage());
        dto.setTimeStamp(LocalDateTime.now());
        dto.setStatus(HttpStatus.NOT_FOUND.toString());
        return dto;
    }

    
    @ExceptionHandler(NotAvailableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDto handleNotAvailable(NotAvailableException ex) {
        ErrorMessageDto dto = new ErrorMessageDto();
        dto.setErrorMsg(ex.getMessage());
        dto.setTimeStamp(LocalDateTime.now());
        dto.setStatus(HttpStatus.NOT_FOUND.toString());
        return dto;
    }
    

    @ExceptionHandler(FriendshipAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessageDto handleFriendshipAlreadyExists(FriendshipAlreadyExistsException ex) {
        ErrorMessageDto dto = new ErrorMessageDto();
        dto.setErrorMsg(ex.getMessage());
        dto.setTimeStamp(LocalDateTime.now());
        dto.setStatus(HttpStatus.CONFLICT.toString());
        return dto;
    }

    
    @ExceptionHandler(InvalidFriendRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto handleInvalidFriendRequest(InvalidFriendRequestException ex) {
        ErrorMessageDto dto = new ErrorMessageDto();
        dto.setErrorMsg(ex.getMessage());
        dto.setTimeStamp(LocalDateTime.now());
        dto.setStatus(HttpStatus.BAD_REQUEST.toString());
        return dto;
    }

    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessageDto handleGenericException(Exception ex) {
        ErrorMessageDto dto = new ErrorMessageDto();
        dto.setErrorMsg("An unexpected error occurred: " + ex.getMessage());
        dto.setTimeStamp(LocalDateTime.now());
        dto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return dto;
    }
}


