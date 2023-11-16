package com.example.ms_user_service.configs;

import com.example.ms_user_service.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(
                        req -> req.antMatchers("/api/signup", "/api/signin", "/api/users/hello").permitAll()
                                .regexMatchers(HttpMethod.GET, "/api/users/\\d+/profile").hasAnyRole(Role.ADMIN.name(), Role.MEMBER.name())
                                .regexMatchers(HttpMethod.PATCH, "/api/users/\\d+/profile").hasRole(Role.MEMBER.name())
                                .regexMatchers(HttpMethod.PATCH, "/api/users/\\d+").hasRole(Role.ADMIN.name())
                                .anyRequest().authenticated()
                ).sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
