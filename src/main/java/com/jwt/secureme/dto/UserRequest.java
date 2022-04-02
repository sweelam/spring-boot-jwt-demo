package com.jwt.secureme.dto;

import com.jwt.secureme.model.AppRole;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Setter
@Getter
@Builder
public class UserRequest {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String username;

    @NotNull
    @Length(min = 5, message = "length must be greater than 4")
    private String password;

    Collection<AppRole> roles;
}
