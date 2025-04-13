package com.goodjob.job.application.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String username = request.getHeader("X-Auth-Username");
        String rolesHeader = request.getHeader("X-Auth-Roles");
        String permissionsHeader = request.getHeader("X-Auth-Permissions");

        if (Objects.nonNull(username) && Objects.nonNull(rolesHeader) && Objects.nonNull(
                permissionsHeader)) {

            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (String role : rolesHeader.split(",")) {
                authorities.add(new SimpleGrantedAuthority(role.trim()));
            }
            for (String permission : permissionsHeader.split(",")) {
                if (permission.contains("JOB")
                        || permission.contains("INDUSTRY")
                        || permission.contains("BENEFIT")
                        || permission.contains("SKILL")
                        || permission.contains("POSTING")
                        || permission.contains("COMPANY")
                ) {
                    authorities.add(new SimpleGrantedAuthority(permission.trim()));
                }
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
