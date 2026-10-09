package com.thanjoz.jobportal_backend.config;

import com.thanjoz.jobportal_backend.security.JwtAuthenticationFilter;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(
            JwtAuthenticationFilter jwtAuthenticationFilter) {

        this.jwtAuthenticationFilter =
                jwtAuthenticationFilter;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173"
        ));

        configuration.setAllowedOriginPatterns(List.of(
                "http://192.168.1.*:5173"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of(
                "Authorization",
                "Content-Type"
        ));

        configuration.setAllowCredentials(false);


        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // ==================================================
                // CSRF
                // ==================================================

                .csrf(csrf ->
                        csrf.disable()
                )


                // ==================================================
                // CORS
                // ==================================================

                .cors(cors ->
                        cors.configurationSource(
                                corsConfigurationSource()
                        )
                )


                // ==================================================
                // SESSION
                // ==================================================

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                // ==================================================
                // AUTHORIZATION
                // ==================================================

                .authorizeHttpRequests(auth -> auth


                        // --------------------------------------------------
                        // OPTIONS
                        // --------------------------------------------------

                        .requestMatchers(
                                HttpMethod.OPTIONS,
                                "/**"
                        ).permitAll()


                        // --------------------------------------------------
                        // PUBLIC USER APIs
                        // --------------------------------------------------

                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/api/jobs",
                                "/api/jobs/search",
                                "/api/job-categories/**"
                        ).permitAll()


                        // --------------------------------------------------
                        // CONTRACTOR REGISTRATION REQUEST
                        // PUBLIC
                        // --------------------------------------------------

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/contractor-requests"

                        ).permitAll()


                        // --------------------------------------------------
                        // SUPER ADMIN CONTRACTOR REQUEST APIs
                        // SUPER ADMIN ONLY
                        // --------------------------------------------------

                        .requestMatchers(
                                "/api/superadmin/contractor-requests/**"
                        ).hasRole("SUPER_ADMIN")


                        // --------------------------------------------------
                        // RESUME VIEW / DOWNLOAD
                        // AUTHENTICATED
                        // --------------------------------------------------

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/applications/resume/**"
                        ).authenticated()


                        // --------------------------------------------------
                        // SUPER ADMIN APIs
                        // --------------------------------------------------

                        .requestMatchers(
                                "/api/superadmin/**"
                        ).hasRole("SUPER_ADMIN")


                        // --------------------------------------------------
                        // CONTRACTOR JOB CREATION
                        // AUTHENTICATED
                        // --------------------------------------------------

                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/jobs/create/**"
                        ).authenticated()


                        // --------------------------------------------------
                        // EVERYTHING ELSE
                        // --------------------------------------------------

                        .anyRequest().authenticated()
                )


                // ==================================================
                // JWT FILTER
                // ==================================================

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );


        return http.build();
    }
}