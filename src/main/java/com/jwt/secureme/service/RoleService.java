package com.jwt.secureme.service;

import com.jwt.secureme.model.AppRole;

import java.util.Optional;

public interface RoleService {
    Optional<AppRole> getRole(String roleName);
    
    Optional<AppRole> addRole(String roleName);

    void deleteRole(String roleName);
}
