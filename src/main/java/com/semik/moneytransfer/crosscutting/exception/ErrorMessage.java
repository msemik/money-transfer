package com.semik.moneytransfer.crosscutting.exception;

import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpStatus;

@Data
public class ErrorMessage {
    private final @NonNull String message;
    private final long status;

    public ErrorMessage(HttpStatus status, Exception ex) {
        this.message = ex.getMessage();
        this.status = status.value();
    }
}
