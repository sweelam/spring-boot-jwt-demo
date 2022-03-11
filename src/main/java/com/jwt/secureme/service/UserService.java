package com.jwt.secureme.service;

import com.jwt.secureme.dto.UserRequest;
import com.jwt.secureme.model.AppUser;

import java.util.List;

public interface UserService {
    AppUser saveNewUser(UserRequest userRequest);

    AppUser getUser(String username);

    List<AppUser> getUsers();

    void deleteUser(String username);

    void addUserRole(String username, String roleName);
}
