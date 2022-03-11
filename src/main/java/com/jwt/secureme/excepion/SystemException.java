package com.jwt.secureme.excepion;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
public class SystemException extends RuntimeException {
    private String message;
    private Instant errorTime = Instant.now();

    public SystemException(String message) {
        this.message = message;
    }
}
