package com.jwt.secureme.repo;

import com.jwt.secureme.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<AppRole, Long> {
    Optional<AppRole> findAppRoleByName(String roleName);
}
