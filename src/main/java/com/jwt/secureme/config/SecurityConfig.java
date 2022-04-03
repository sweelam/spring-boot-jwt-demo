package com.jwt.secureme.config;

import com.jwt.secureme.dto.SystemRoles;
import com.jwt.secureme.filter.SystemAuthenticationFilter;
import com.jwt.secureme.filter.SystemAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String APPLICATION_NAME = "jwt-demo";
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${token.secret.key}")
    private String secretKey;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Testing config
        var encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder)
                .withUser("spring")
                .password(encoder.encode("secret"))
                .roles(SystemRoles.USER.name());

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs**").permitAll()
                .and()
                .authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/**").hasAuthority(SystemRoles.USER.name())
                .and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/**").hasAuthority(SystemRoles.ADMIN.name())
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(new SystemAuthorizationFilter(secretKey), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new SystemAuthenticationFilter(authenticationManagerBean(), secretKey));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

}
