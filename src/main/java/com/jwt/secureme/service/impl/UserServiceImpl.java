package com.jwt.secureme.service.impl;

import com.jwt.secureme.dto.AdminDetails;
import com.jwt.secureme.dto.SystemRoles;
import com.jwt.secureme.dto.UserRequest;
import com.jwt.secureme.excepion.SystemException;
import com.jwt.secureme.model.AppUser;
import com.jwt.secureme.repo.UserRepo;
import com.jwt.secureme.service.RoleService;
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
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepo userRepo;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final AdminDetails adminDetails;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findAppUserByUsername(username)
                .map(this::principleUserConversion)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User %s not found in the database", username)));
    }

    @Override
    public User principleUserConversion(AppUser appUser) {
        List<SimpleGrantedAuthority> grantAuths = new ArrayList<>();
        appUser.getRoles().forEach(t -> grantAuths.add(new SimpleGrantedAuthority(t.getName())));
        return new User(appUser.getUsername(), appUser.getPassword(), grantAuths);
    }

    @Override
    public void setupAdmin() {
        try {

            var user = addNewUser(
                    UserRequest.builder()
                            .name(adminDetails.name())
                            .username(adminDetails.username())
                            .password(adminDetails.password()).build()
            );

            if (CollectionUtils.isEmpty(user.getRoles())) {
                addRole();
            }
        } catch (Exception e) {
            log.warn("Adding user failed {}", e.getMessage());
        }
    }

    private void addRole() {
        try {

            if (!roleService.getRole(SystemRoles.ADMIN.name()).isPresent()) {
                roleService.addRole(SystemRoles.ADMIN.name());
            }

            addUserRole("msweelam", SystemRoles.ADMIN.name());
        } catch (Exception e) {
            log.warn("Adding roles failed {}", e.getMessage());
        }
    }

    @Override
    public AppUser addNewUser(UserRequest user) {
        if (userRepo.findAppUserByUsername(user.getUsername()).isPresent())
            throw new SystemException(String.format("User %s already exist", user.getUsername()));

        var newUser = userRepo.save(
                    AppUser.builder()
                            .name(user.getName())
                            .username(user.getUsername())
                            .password(passwordEncoder.encode(user.getPassword()))
                            .build()
                );

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> addUserRole(user.getUsername(), role.getName()));
        }

        return newUser;
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
    public void removeUser(String username) {
        userRepo.delete(
                userRepo.findAppUserByUsername(username)
                        .orElseThrow(() -> new SystemException(String.format("User %s doesn't exist", username)))
        );
    }

    @Override
    public void addUserRole(String username, String roleName) {
        userRepo.findAppUserByUsername(username)
                .map(user ->
                        user.getRoles().add(roleService.getRole(roleName)
                                .orElseGet(() -> roleService.addRole(roleName)
                                        .orElseThrow(() -> new SystemException(String.format("User role %s doesn't exist", roleName))))
                        )
                ).orElseThrow(() -> new SystemException(String.format("User %s doesn't exist", username)));
    }
}
