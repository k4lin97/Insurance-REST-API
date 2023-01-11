package com.kalin.insurance.insurancerestapi.system.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Holds data for apis errors.
 */
public class ApiError {
    private final LocalDateTime timestamp;
    private final int status;
    private final HttpStatus error;
    private final List<String> messages;

    public ApiError(HttpStatus error, List<String> messages) {
        this.timestamp = LocalDateTime.now();
        this.error = error;
        this.status = this.error.value();
        this.messages = messages;
    }

    public ApiError(HttpStatus error, String message) {
        this.timestamp = LocalDateTime.now();
        this.error = error;
        this.status = this.error.value();
        this.messages = Collections.singletonList(message);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public HttpStatus getError() {
        return error;
    }

    public List<String> getMessages() {
        return messages;
    }
}