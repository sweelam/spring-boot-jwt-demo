package com.jwt.secureme.dto;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "admin.details")
@ConstructorBinding
public record AdminDetails (String name, String username, String password) {}