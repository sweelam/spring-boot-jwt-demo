package com.jwt.secureme.service.impl;

import com.jwt.secureme.dto.UserRequest;
import com.jwt.secureme.excepion.SystemException;
import com.jwt.secureme.model.AppUser;
import com.jwt.secureme.repo.RoleRepo;
import com.jwt.secureme.repo.UserRepo;
import com.jwt.secureme.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findAppUserByUsername(username)
                .map((appUser) -> {
                    List<SimpleGrantedAuthority> grantAuths = new ArrayList();
                    appUser.getRoles().forEach((t) -> grantAuths.add(new SimpleGrantedAuthority(t.getName())));
                    return new User(appUser.getUsername(), appUser.getPassword(), grantAuths);
                }).orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found in the database", username)));
    }

    @Override
    public AppUser saveNewUser(UserRequest user) {
        if (userRepo.findAppUserByUsername(user.getUsername()).isPresent())
            throw new SystemException(String.format("User %s already exist", user.getUsername()));

        return userRepo.save(
                AppUser.builder()
                        .name(user.getName())
                        .username(user.getUsername())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .build()
        );
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
