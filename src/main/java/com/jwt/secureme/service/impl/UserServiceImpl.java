package com.jwt.secureme.service.impl;

import com.jwt.secureme.excepion.SystemException;
import com.jwt.secureme.model.AppUser;
import com.jwt.secureme.repo.RoleRepo;
import com.jwt.secureme.repo.UserRepo;
import com.jwt.secureme.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Override
    public AppUser saveNewUser(AppUser user) {
        if (userRepo.findAppUserByUsername(user.getUsername()).isPresent())
            throw new SystemException(String.format("User %s already exist", user.getUsername()));

        return userRepo.save(user);
    }

    @Override
    public AppUser getUser(String username) {
        return userRepo.findAppUserByUsername(username).orElse(new AppUser());
    }

    @Override
    public List<AppUser> getUsers() {
        return userRepo.findAll();
    }

    @Override
    public void deleteUser(String username) {
        userRepo.delete(
                userRepo.findAppUserByUsername(username)
                        .orElseThrow(() -> new SystemException(String.format("User %s doesn't exist", username)))
        );
    }

    @Override
    public void addUserRole(String username, String roleName) {
        userRepo.findAppUserByUsername(username)
                .map((user) ->
                        user.getRoles().add(roleRepo.findAppRoleByName(roleName)
                                .orElseThrow(() -> new SystemException(String.format("User role %s doesn't exist", roleName)))
                        )
                ).orElseThrow(() -> new SystemException(String.format("User %s doesn't exist", username)));
    }
}
