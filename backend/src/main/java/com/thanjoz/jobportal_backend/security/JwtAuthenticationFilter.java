package com.thanjoz.jobportal_backend.security;

import com.thanjoz.jobportal_backend.entity.User;
import com.thanjoz.jobportal_backend.repository.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserRepository userRepository) {

        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        System.out.println("====================================");
        System.out.println(
                "REQUEST : "
                        + request.getMethod()
                        + " "
                        + request.getRequestURI()
        );

        System.out.println("AUTH HEADER : " + authHeader);

        if (authHeader == null ||
                !authHeader.startsWith("Bearer ")) {

            System.out.println("NO JWT TOKEN FOUND");

            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        System.out.println("JWT TOKEN FOUND");

        if (!jwtService.isTokenValid(token)) {

            System.out.println("JWT TOKEN IS INVALID");

            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("JWT TOKEN IS VALID");

        String username = jwtService.extractUsername(token);

        System.out.println(
                "USERNAME FROM TOKEN : " + username
        );

        User user = userRepository
                .findByEmail(username)
                .orElse(null);

        if (user == null) {

            System.out.println(
                    "USER NOT FOUND : " + username
            );

            filterChain.doFilter(request, response);
            return;
        }

        System.out.println(
                "USER FOUND : " + user.getEmail()
        );

        System.out.println(
                "USER ROLE : " + user.getRole()
        );

        String role = user.getRole().name();

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        List.of(
                                new SimpleGrantedAuthority("ROLE_" + role)
                        )
                );

        authentication.setDetails(
                new WebAuthenticationDetailsSource()
                        .buildDetails(request)
        );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        System.out.println(
                "AUTHENTICATION SET : "
                        + SecurityContextHolder
                        .getContext()
                        .getAuthentication()
        );

        filterChain.doFilter(request, response);
    }
}