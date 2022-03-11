package com.jwt.secureme.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RoleRequest {
    private String name;
}
