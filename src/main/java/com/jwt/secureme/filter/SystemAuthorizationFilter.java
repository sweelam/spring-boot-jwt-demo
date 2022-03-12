package com.jwt.secureme.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

@RequiredArgsConstructor
public class SystemAuthorizationFilter extends OncePerRequestFilter {
    private final String SECRET_KEY;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login")) {
            filterChain.doFilter(request, response);
        } else {
            var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                var token = authHeader.split(" ")[1];
                Algorithm alg = Algorithm.HMAC256(SECRET_KEY.getBytes());
                var verifier = JWT.require(alg).build();
                var decodedIwt = verifier.verify(token);
                var username = decodedIwt.getSubject();
                var roles = decodedIwt.getClaim("roles").asArray(String.class);
                List<SimpleGrantedAuthority> grantAuths = new ArrayList();
                stream(roles).forEach((t) -> grantAuths.add(new SimpleGrantedAuthority(t)));
                var authToken =
                        new UsernamePasswordAuthenticationToken(username, null, grantAuths);
                SecurityContextHolder.getContext().setAuthentication(authToken);
                filterChain.doFilter(request, response);
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
