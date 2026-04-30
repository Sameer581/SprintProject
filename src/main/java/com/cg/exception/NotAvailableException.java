package com.cg.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotAvailableException extends RuntimeException {

    private final String resourceName;
    private final String reason;

    // Detailed constructor (your version)
    public NotAvailableException(String resourceName, String reason) {
        super(String.format("%s is not available : %s", resourceName, reason));
        this.resourceName = resourceName;
        this.reason = reason;
    }

    // Simple constructor (his version)
    public NotAvailableException(String message) {
        super(message);
        this.resourceName = null;
        this.reason = null;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getReason() {
        return reason;
    }
}