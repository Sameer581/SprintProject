package com.cg.exception;

public class UnauthorizedException extends RuntimeException {

    private String action;

    public UnauthorizedException(String action) {
        super(String.format("Unauthorized : You are not allowed to %s", action));
        this.action = action;
    }

    public String getAction() { 
    	return action; 
    	}
}