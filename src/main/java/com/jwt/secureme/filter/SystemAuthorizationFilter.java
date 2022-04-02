package com.jwt.secureme.filter;

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

import static com.jwt.secureme.util.JwtUtils.decodeToken;
import static java.util.Arrays.stream;

@RequiredArgsConstructor
public class SystemAuthorizationFilter extends OncePerRequestFilter {
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/login")) {
            filterChain.doFilter(request, response);
        } else {
            var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                var decodedJwt = decodeToken(authHeader, secretKey);
                var roles = decodedJwt.getClaim("roles").asArray(String.class);
                List<SimpleGrantedAuthority> grantAuths = new ArrayList<>();
                stream(roles).forEach(t -> grantAuths.add(new SimpleGrantedAuthority(t)));
                var authToken =
                        new UsernamePasswordAuthenticationToken(decodedJwt.getSubject(), null, grantAuths);

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        }
    }
}
