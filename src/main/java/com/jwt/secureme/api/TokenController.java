package com.jwt.secureme.api;

import com.auth0.jwt.algorithms.Algorithm;
import com.jwt.secureme.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.jwt.secureme.config.ApiConfigs.SYS_HEADER;
import static com.jwt.secureme.util.JwtUtils.decodeToken;
import static com.jwt.secureme.util.JwtUtils.generateToken;

@RestController
@RequestMapping(value = "/token", headers = SYS_HEADER)
@RequiredArgsConstructor
public class TokenController {
    private final UserService userService;

    @Value("${token.secret.key}")
    private String secretKey;

    @GetMapping
    public ResponseEntity<String> refreshToken(@RequestHeader("AUTHORIZATION") String token) {
        var decodedJwt = decodeToken(token, secretKey);
        var user = userService.principleUserConversion(userService.getUser(decodedJwt.getSubject()));
        var newToken =
                generateToken("jwt-demo-app", user, Algorithm.HMAC256(secretKey.getBytes()), 10);

        HttpHeaders headers = new HttpHeaders();
        headers.add("access_token", newToken);
        return ResponseEntity.ok()
                .headers(headers)
                .body("Token is refreshed");
    }
}
