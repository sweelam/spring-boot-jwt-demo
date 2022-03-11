package com.jwt.secureme.service;

import com.jwt.secureme.model.AppRole;

public interface RoleService {
    AppRole addRole(String roleName);

    void deleteRole(String roleName);
}
