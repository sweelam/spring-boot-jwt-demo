package com.jwt.secureme.excepion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Builder
public class RestExceptionResponse {
    private String message;
    @Builder.Default
    private Instant errorTime = Instant.now();
}
