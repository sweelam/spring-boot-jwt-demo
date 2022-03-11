package com.jwt.secureme.excepion;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class SystemException extends RuntimeException {
    private String message;
    private Instant errorTime = Instant.now();
    private Throwable cause;

    public SystemException(String message) {
        this.message = message;
    }

    public SystemException(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }
}
