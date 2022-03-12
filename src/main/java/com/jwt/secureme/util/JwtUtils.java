package com.jwt.secureme.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jwt.secureme.excepion.SystemException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Date;
import java.util.stream.Collectors;

public class JwtUtils {
    private JwtUtils() {}

    public static DecodedJWT decodeToken(String token, final String SECRET_KEY) {
        if (token != null && token.startsWith("Bearer ")) {
            try {
                Algorithm alg = Algorithm.HMAC256(SECRET_KEY.getBytes());
                return JWT.require(alg).build().verify(token.split(" ")[1]);
            } catch (JWTVerificationException | IllegalArgumentException e) {
                throw new JWTVerificationException("Can't decode token provided");
            }
        } else {
            throw new SystemException("Token provided isn't correct");
        }
    }

    public static String generateToken(String issuer, User user, Algorithm algorithm, int expireAfterMins) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + expireAfterMins * 60 * 1000))
                .withIssuer(issuer)
                .withClaim("roles", user.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm);
    }
}
