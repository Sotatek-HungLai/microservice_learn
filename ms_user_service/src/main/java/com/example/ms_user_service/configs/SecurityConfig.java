package com.example.ms_user_service.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Qualifier("delegatedAuthenticationEntryPoint")
    private final AuthenticationEntryPoint authEntryPoint;

    private final JwtAuthFilter jwtAuthFilter;

    private final PermitAllRequestsConfig permitAllRequestsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(
                        req -> req.antMatchers(permitAllRequestsConfig.getPermitAllAntMatchers()).permitAll()
                                .anyRequest().authenticated()
                ).exceptionHandling().authenticationEntryPoint(authEntryPoint).and().
                sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
