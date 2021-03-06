package com.jwt.secureme.api;

import com.jwt.secureme.dto.UserRequest;
import com.jwt.secureme.model.AppUser;
import com.jwt.secureme.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static com.jwt.secureme.config.ApiConfig.SYS_HEADER;
import static com.jwt.secureme.config.SecurityConfig.APPLICATION_NAME;

@RestController
@RequestMapping(value = "/user", headers = SYS_HEADER)
@SecurityRequirement(name = APPLICATION_NAME)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<AppUser> addUser(@Valid @RequestBody UserRequest user) throws URISyntaxException {
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .build(new URI("api/user"))
        ).body(userService.addNewUser(user));
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/role/{username}/{roleName}")
    public ResponseEntity<String> addUserRole(@PathVariable String username, @PathVariable String roleName) throws URISyntaxException {
        userService.addUserRole(username, roleName);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .build(new URI("api/user/role"))
        ).body("Role is added to user");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> removeUser(@PathVariable String username) {
        userService.removeUser(username);
        return ResponseEntity.ok(String.format("User %s is removed", username));
    }
}
