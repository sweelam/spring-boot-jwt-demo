package com.jwt.secureme.excepion;

import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {
    private String message;

    public SystemException(String message) {
        this.message = message;
    }
}
