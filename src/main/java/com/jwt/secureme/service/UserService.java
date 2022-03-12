package com.jwt.secureme.service;

import com.jwt.secureme.dto.UserRequest;
import com.jwt.secureme.model.AppUser;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface UserService {
    AppUser addNewUser(UserRequest userRequest);

    AppUser getUser(String username);

    List<AppUser> getUsers();

    void removeUser(String username);

    void addUserRole(String username, String roleName);

    User principleUserConversion(AppUser appUser);
}
