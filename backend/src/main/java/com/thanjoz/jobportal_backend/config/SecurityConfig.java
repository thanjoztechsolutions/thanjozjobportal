package com.thanjoz.jobportal_backend.config;

import com.thanjoz.jobportal_backend.security.JwtAuthenticationFilter;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfig {


    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http

                // Disable CSRF because JWT is used
                .csrf(csrf -> csrf.disable())


                // No session storage
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )


                .authorizeHttpRequests(auth -> auth


                        // Public APIs
                        .requestMatchers(
                                "/api/auth/register",
                                "/api/auth/login"
                        ).permitAll()



                        // JOB GET APIs - Public
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/jobs/**"
                        ).permitAll()



                        // JOB CREATE UPDATE DELETE - JWT Required
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/jobs/**"
                        ).authenticated()


                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/jobs/**"
                        ).authenticated()


                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/jobs/**"
                        ).authenticated()



                        // Saved Jobs and Reviews
                        .requestMatchers(
                                "/api/saved-jobs/**",
                                "/api/reviews/**"
                        ).permitAll()



                        // USER and ADMIN
                        .requestMatchers(
                                "/api/auth/profile/**",
                                "/api/auth/change-password",
                                "/api/companies/**"
                        ).hasAnyRole("USER", "ADMIN")



                        // ADMIN only
                        .requestMatchers(
                                "/api/users/{id}/role",
                                "/api/auth/{id}",
                                "/api/auth"
                        ).hasRole("ADMIN")



                        // Any other request
                        .anyRequest().authenticated()


                )


                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();

    }



    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }



    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();

    }

}