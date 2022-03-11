package com.jwt.secureme.service.impl;

import com.jwt.secureme.excepion.SystemException;
import com.jwt.secureme.model.AppRole;
import com.jwt.secureme.repo.RoleRepo;
import com.jwt.secureme.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepo roleRepo;

    @Override
    public Optional<AppRole> getRole(String roleName) {
        return roleRepo.findAppRoleByName(roleName);
    }

    @Override
    public Optional<AppRole> addRole(String roleName) {
        if (roleRepo.findAppRoleByName(roleName).isPresent())
            throw new SystemException(String.format("Role %s already exist", roleName));

        return Optional.of(roleRepo.save(AppRole.builder().name(roleName).build()));
    }

    @Override
    public void deleteRole(String roleName) {
        roleRepo.delete(
                roleRepo.findAppRoleByName(roleName)
                        .orElseThrow(() -> new SystemException(String.format("Role %s doesn't exist", roleName)))
        );
    }
}
