package com.jwt.secureme.service.impl;

import com.jwt.secureme.excepion.SystemException;
import com.jwt.secureme.model.AppRole;
import com.jwt.secureme.repo.RoleRepo;
import com.jwt.secureme.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    @Override
    public AppRole addRole(String roleName) {
        if (roleRepo.findAppRoleByName(roleName).isPresent())
            throw new SystemException(String.format("Role %s already exist", roleName));

        var role = new AppRole();
        role.setName(roleName);
        return roleRepo.save(role);
    }

    @Override
    public void deleteRole(String roleName) {
        roleRepo.delete(
                roleRepo.findAppRoleByName(roleName)
                        .orElseThrow(() -> new SystemException(String.format("User role %s doesn't exist", roleName)))
        );
    }
}
