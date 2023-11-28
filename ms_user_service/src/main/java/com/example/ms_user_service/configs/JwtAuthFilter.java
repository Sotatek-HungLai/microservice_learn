package com.example.ms_user_service.configs;

import com.example.ms_user_service.dtos.JwtVerifyResponseDTO;
import com.example.ms_user_service.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;

    private final AuthService authService;

    private final PermitAllRequestsConfig permitAllRequestsConfig;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        System.out.println("doFilterInternal : " + request.getRequestURI());

        if (permitAllRequestsConfig.isPermitAllRequest(request.getRequestURI(), HttpMethod.resolve(request.getMethod()))) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }
        JwtVerifyResponseDTO jwtVerifyResponseDTO = authService.validateToken(authorizationHeader);
        String email = jwtVerifyResponseDTO.gmail();
        if (email != null || SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            List<GrantedAuthority> grantedAuthorityList = (List<GrantedAuthority>) userDetails.getAuthorities();
            grantedAuthorityList.add(new SimpleGrantedAuthority("ROLE_LOGGED_IN"));
            log.info("grantedAuthorityList : {}", grantedAuthorityList.toString());
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails.getUsername(),
                    null,
                    grantedAuthorityList
            );
            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

}
