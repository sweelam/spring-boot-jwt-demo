package com.jwt.secureme.api;

import com.jwt.secureme.dto.UserRequest;
import com.jwt.secureme.model.AppUser;
import com.jwt.secureme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<AppUser> addUser(@Valid @RequestBody UserRequest user) throws URISyntaxException {
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .build(new URI("api/user"))
        ).body(userService.saveNewUser(user));
    }

    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
}
