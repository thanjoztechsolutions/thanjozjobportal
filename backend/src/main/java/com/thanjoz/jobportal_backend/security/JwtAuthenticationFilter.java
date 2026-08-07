package com.thanjoz.jobportal_backend.security;


import com.thanjoz.jobportal_backend.jwt.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;



@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {



    @Autowired
    private JwtService jwtService;



    @Autowired
    private UserDetailsService userDetailsService;




    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {



        String authHeader = request.getHeader("Authorization");


        System.out.println("AUTH HEADER : " + authHeader);



        String token = null;

        String username = null;



        if(authHeader != null && authHeader.startsWith("Bearer ")) {


            token = authHeader.substring(7);


            try {

                username = jwtService.extractUsername(token);

                System.out.println("JWT USERNAME : " + username);


            } catch(Exception e) {

                System.out.println("JWT ERROR : " + e.getMessage());

            }

        }



        if(username != null &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null) {



            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(username);



            if(jwtService.isTokenValid(token, username)) {



                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );



                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );



                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authenticationToken);



                System.out.println("JWT AUTHENTICATION SUCCESS");

            }
            else {

                System.out.println("JWT INVALID");

            }

        }



        filterChain.doFilter(request,response);

    }


}