package com.cg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotAvailableException extends RuntimeException{

	public NotAvailableException(String message) {
		super(message);
	}
	

}
